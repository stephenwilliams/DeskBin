package com.alta189.hasteclient.url;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class IsGdService implements ShorteningService {
	private static final String NAME = "is.gd";
	private static final String SHORTEN_URL = "http://is.gd/create.php?logstats=1&format=simple&url=";
	private static final String EXPAND_URL = "http://is.gd/forward.php?format=simple&shorturl=";
	private final HttpClient httpClient = new DefaultHttpClient();

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public String shorten(String url) throws ShortenerException {
		String raw = null;
		try {
			HttpGet request = new HttpGet(SHORTEN_URL + URLEncoder.encode(url, "UTF-8"));
			HttpResponse response = httpClient.execute(request);
			raw = EntityUtils.toString(response.getEntity());
			if (raw.startsWith("Error")) {
				throw new ShortenerException(raw);
			}
		} catch (ClientProtocolException e) {
			throw new ShortenerException(e);
		} catch (UnsupportedEncodingException e) {
			throw new ShortenerException(e);
		} catch (IOException e) {
			throw new ShortenerException(e);
		}
		return raw;
	}

	@Override
	public String expand(String url) throws ShortenerException {
		String raw = null;
		try {
			HttpGet request = new HttpGet(EXPAND_URL + URLEncoder.encode(url, "UTF-8"));
			HttpResponse response = httpClient.execute(request);
			raw = EntityUtils.toString(response.getEntity());
			if (raw.startsWith("Error")) {
				throw new ShortenerException(raw);
			}
		} catch (ClientProtocolException e) {
			throw new ShortenerException(e);
		} catch (UnsupportedEncodingException e) {
			throw new ShortenerException(e);
		} catch (IOException e) {
			throw new ShortenerException(e);
		}
		return raw;
	}
}
