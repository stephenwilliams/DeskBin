package com.alta189.hasteclient.paste;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class PastebinService extends AbstractPasteService {
	private static final String URL = "";
	private final HttpClient client = new DefaultHttpClient();
	private final String apiKey;

	public PastebinService(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String paste(String content, boolean isPrivate) throws PasteException {
		HttpPost post = new HttpPost("http://pastebin.com/api/api_post.php");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("api_dev_key", apiKey));
		nvps.add(new BasicNameValuePair("api_option", "paste"));
		nvps.add(new BasicNameValuePair("api_paste_private", isPrivate ? "2" : "0"));
		nvps.add(new BasicNameValuePair("api_paste_code", content));
		try {
			post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new PasteException(e);
		}
		try {
			HttpResponse response = client.execute(post);
			return EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			throw new PasteException(e);
		}
	}
}
