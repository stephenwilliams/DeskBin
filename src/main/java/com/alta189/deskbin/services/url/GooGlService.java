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
import java.net.URL;

import net.petersson.googl.GooGl;
import net.petersson.googl.GooGlException;

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.KeyUtils;

public class GooGlService extends ShorteningService {
	private static final String NAME = "goo.gl";
	private final GooGl googl;

	public GooGlService() {
		String apiKey = KeyUtils.getKey("googl-apikey");
		googl = new GooGl(apiKey);
	}

	public GooGlService(ServiceSnapshot snapshot) {
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
	public String shorten(String url) throws ShortenerException {
		try {
			return googl.shorten(new URL(url)).toString();
		} catch (IOException e) {
			throw new ShortenerException(e);
		} catch (GooGlException e) {
			throw new ShortenerException(e);
		}
	}

	@Override
	public String expand(String url) throws ShortenerException {
		try {
			return googl.expand(new URL(url)).toString();
		} catch (IOException e) {
			throw new ShortenerException(e);
		} catch (GooGlException e) {
			throw new ShortenerException(e);
		}
	}
}
