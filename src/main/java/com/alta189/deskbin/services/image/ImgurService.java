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
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyUtils;

public class ImgurService extends ImageService {
	private static final String NAME = "imgur";
	private static final String UPLOAD_URL = "http://api.imgur.com/2/upload.json";
	private final HttpClient client = new DefaultHttpClient();
	private final String apikey;

	public ImgurService() {
		this.apikey = KeyUtils.getKey("imgur-apikey");
	}

	public ImgurService(ServiceSnapshot snapshot) {
		this();
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public ServiceSnapshot generateSnapshot() {
		return new ServiceSnapshot(getClass());
	}

	@Override
	public String upload(BufferedImage image) throws ImageServiceException {
		try {
			File file = File.createTempFile("screenshot-", ".png");
			ImageIO.write(image, "png", file);
			return upload(file);
		} catch (ImageServiceException e) {
		    throw e;
		}catch (IOException e) {
			throw new ImageServiceException(e);
		} catch (Exception e) {
			throw new ImageServiceException(e);
		}
	}

	@Override
	public String upload(List<BufferedImage> images) throws ImageServiceException {
		StringBuilder result = new StringBuilder();
		for (BufferedImage image : images) {
			result.append(upload(image))
					.append(" ");
		}
		return result.toString();
	}

	@Override
	public String upload(File image) throws ImageServiceException {
		try {
			HttpPost post = new HttpPost(UPLOAD_URL);

			MultipartEntity entity = new MultipartEntity();
			entity.addPart("key", new StringBody(apikey, Charset.forName("UTF-8")));
			entity.addPart("type", new StringBody("file", Charset.forName("UTF-8")));
			FileBody fileBody = new FileBody(image);
			entity.addPart("image", fileBody);
			post.setEntity(entity);

			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new ImageServiceException("Error accessing imgur. Status code: " + response.getStatusLine().getStatusCode());
			}
			String json = EntityUtils.toString(response.getEntity());
			JSONObject jsonObject = new JSONObject(json).getJSONObject("upload").getJSONObject("links");
			return jsonObject.getString("imgur_page");
		} catch (Exception e) {
			throw new ImageServiceException(e);
		}
	}

	@Override
	public String upload(Collection<File> images) throws ImageServiceException {
		StringBuilder result = new StringBuilder();
		for (File image : images) {
			result.append(upload(image))
					.append(" ");
		}
		return result.toString();
	}
}
