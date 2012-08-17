/*
 * This file is part of DeskBin.
 *
 * Copyright (c) ${project.inceptionYear}-2012, alta189 <http://github.com/alta189/DeskBin/>
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
package com.alta189.deskbin.services.paste;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyUtils;

public class PastebinService extends PasteService {
	private static final String NAME = "pastebin";
	private static final String URL = "http://pastebin.com/api/api_post.php";
	private final HttpClient client = new DefaultHttpClient();
	private final String apiKey;

	public PastebinService() {
		this.apiKey = KeyUtils.getKey("pastebin-apikey");
	}

	public PastebinService(ServiceSnapshot snapshot) {
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
	public String paste(String content, boolean isPrivate) throws PasteException {
		HttpPost post = new HttpPost(URL);

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
