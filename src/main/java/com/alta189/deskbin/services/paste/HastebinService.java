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

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.alta189.deskbin.services.ServiceSnapshot;

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
