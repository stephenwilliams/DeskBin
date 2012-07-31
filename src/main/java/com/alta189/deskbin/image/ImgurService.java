package com.alta189.deskbin.image;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ImgurService implements ImageService {
	private static final String NAME = "imgur";
	private static final String UPLOAD_URL = "http://api.imgur.com/2/upload.json";
	private final HttpClient client = new DefaultHttpClient();
	private final String apikey;

	public ImgurService(String apikey) {
		this.apikey = apikey;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String upload(BufferedImage image) throws ImageServiceException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);

			HttpPost post = new HttpPost(UPLOAD_URL);

			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("key", apikey));
			nvps.add(new BasicNameValuePair("image", URLEncoder.encode(Base64.encodeBase64String(baos.toByteArray()), "UTF-8")));
			nvps.add(new BasicNameValuePair("type", "base64"));

			post.setEntity(new UrlEncodedFormEntity(nvps));

			HttpResponse response = client.execute(post);
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String upload(List<BufferedImage> image) throws ImageServiceException {
		return null;
	}
}
