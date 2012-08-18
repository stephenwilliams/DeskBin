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
package com.alta189.deskbin.services.url;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alta189.deskbin.services.ServiceSnapshot;

public class IsGdService extends ShorteningService {
	private static final String NAME = "is.gd";
	private static final Pattern pattern = Pattern.compile("(|http://|https://)(|www\\.)is\\.gd/.*");
	private static final String SHORTEN_URL = "http://is.gd/create.php?logstats=1&format=simple&url=";
	private static final String EXPAND_URL = "http://is.gd/forward.php?format=simple&shorturl=";
	private final HttpClient httpClient = new DefaultHttpClient();

	public IsGdService() {
	}

	public IsGdService(ServiceSnapshot snapshot) {
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

	@Override
	public Pattern getPattern() {
		return pattern;
	}

	@Override
	public boolean isShortenedURL(String url) {
		return pattern.matcher(url).matches();
	}

	@Override
	public String getShortURL(String input) {
		Matcher matcher = pattern.matcher(input);
		if (matcher.find()) {
			return matcher.group();
		}
		return null;
	}
}
