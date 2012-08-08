package com.alta189.deskbin.service.image;

import com.alta189.deskbin.service.ServiceSnapshot;
import com.alta189.deskbin.util.KeyUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;

public class ImgurService extends ImageService {
	private static final String NAME = "imgur";
	private static final String UPLOAD_URL = "http://api.imgur.com/2/upload.json";
	private final HttpClient client = new DefaultHttpClient();
	private final String apikey;

	public ImgurService(String apikey) {
		this.apikey = apikey;
	}

	public ImgurService(ServiceSnapshot snapshot) {
		super(snapshot);
		this.apikey = KeyUtils.getKey("imgur-apikey");
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
