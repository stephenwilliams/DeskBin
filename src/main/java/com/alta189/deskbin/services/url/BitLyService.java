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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyStore;
import com.rosaloves.bitlyj.Bitly;

public class BitLyService extends ShorteningService {
	private static final String NAME = "bit.ly";
	private static final Pattern pattern = Pattern.compile("(|http://|https://)(|www\\.)bit\\.ly/.*");
	private final Bitly.Provider bitly;
	private final String user;

	public BitLyService() {
		user = KeyStore.get("bitly-user");
		String apiKey = KeyStore.get("bitly-apikey");
		bitly = Bitly.as(user, apiKey);
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
		try {
			return bitly.call(Bitly.shorten(url)).getShortUrl();
		} catch (Exception e) {
			throw new ShortenerException(e);
		}
	}

	@Override
	public String expand(String url) throws ShortenerException {
		try {
			return bitly.call(Bitly.shorten(url)).getShortUrl();
		} catch (Exception e) {
			throw new ShortenerException(e);
		}
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
