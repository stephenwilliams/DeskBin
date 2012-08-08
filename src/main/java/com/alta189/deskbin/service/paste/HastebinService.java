package com.alta189.deskbin.service.paste;

import com.alta189.deskbin.service.ServiceSnapshot;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class HastebinService extends AbstractPasteService {
	private static final String NAME = "hastebin";
	private static final String URL = "http://hastebin.com/documents";

	public HastebinService() {
	}

	public HastebinService(ServiceSnapshot snapshot) {
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
	public String paste(String content, boolean isPrivate) throws PasteException {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL);
		try {
			post.setEntity(new StringEntity(content));
			HttpResponse response = client.execute(post);
			String result = EntityUtils.toString(response.getEntity());
			return "http://hastebin.com/" + new JSONObject(result).getString("key");
		} catch (UnsupportedEncodingException e) {
			throw new PasteException(e);
		} catch (ClientProtocolException e) {
			throw new PasteException(e);
		} catch (IOException e) {
			throw new PasteException(e);
		} catch (JSONException e) {
			throw new PasteException(e);
		}
	}

	public class Hastebin {
		private String key;

		public String getKey() {
			return key;
		}
	}
}
