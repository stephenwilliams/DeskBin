/*
 * This file is part of DeskBin.
 *
 * Copyright (c) 2012, alta189 <http://github.com/alta189/DeskBin/>
 * DeskBin is licensed under the GNU Lesser General Public License.
 *
 * DeskBin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DeskBin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.deskbin.services.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import javax.imageio.ImageIO;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyStore;
import com.alta189.deskbin.util.KeyUtils;
import org.apache.commons.io.IOUtils;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;

public class TwitterService extends ImageService {
	public static final String NAME = "Twitter";
	public static final String FILENAME = "capture.png";
	private final Twitter twitterService;
	private MediaProvider imageService;

	public TwitterService() {
		this(MediaProvider.TWITTER);
	}

	public TwitterService(MediaProvider provider) {
		twitterService = new TwitterFactory().getInstance();
		String key = KeyUtils.getKey("twitter-consumerkey");
		String secret = KeyUtils.getKey("twitter-consumersec");
		twitterService.setOAuthConsumer(key, secret);
		AccessToken token = KeyStore.get("twitter-oauth");
		twitterService.setOAuthAccessToken(token);
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String upload(BufferedImage image) throws ImageServiceException {
		try {
			ImageUpload upload = new ImageUploadFactory().getInstance(imageService, twitterService.getAuthorization());
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);
			InputStream input = IOUtils.toInputStream(IOUtils.toString(baos.toByteArray(), "UTF-8"), "UTF-8");
			return upload.upload(FILENAME, input);
		} catch (Exception e) {
			throw new ImageServiceException(e);
		}
	}

	@Override
	public String upload(List<BufferedImage> images) throws ImageServiceException {
		StringBuilder urls = new StringBuilder();
		for (BufferedImage i : images)
			urls.append(upload(i)).append(" ");
		return urls.substring(0, urls.length() - 1);
	}

	@Override
	public ServiceSnapshot generateSnapshot() {
		return new ServiceSnapshot(getClass());
	}

	@Override
	public String upload(File image) throws ImageServiceException {
		try {
			ImageUpload upload = new ImageUploadFactory().getInstance(imageService, twitterService.getAuthorization());
			return upload.upload(image);
		} catch (Exception e) {
			throw new ImageServiceException(e);
		}
	}

	@Override
	public String upload(Collection<File> images) throws ImageServiceException {
		StringBuilder urls = new StringBuilder();
		for (File i : images) {
			urls.append(upload(i)).append(" ");
		}
		return urls.substring(0, urls.length() - 1);
	}
}
