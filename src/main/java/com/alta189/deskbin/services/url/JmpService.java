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

import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.util.CastUtil;
import com.alta189.deskbin.util.KeyStore;
import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.Jmp;

public class JmpService extends ShorteningService {
	private static final String NAME = "j.mp";
	private Bitly.Provider jmp;
	private final String user;

	public JmpService(String user) {
		String apiKey = KeyStore.get("jmp-" + user);
		jmp = Jmp.as(user, apiKey);
		this.user = user;
	}

	public JmpService(ServiceSnapshot snapshot) {
		 this(CastUtil.safeCast(snapshot.get("user"), String.class));
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public ServiceSnapshot generateSnapshot() {
		return new ServiceSnapshot(getClass()).add("user", user);
	}

	@Override
	public String shorten(String url) throws ShortenerException {
		try {
			return jmp.call(Bitly.shorten(url)).getShortUrl();
		} catch (Exception e) {
			throw new ShortenerException(e);
		}
	}

	@Override
	public String expand(String url) throws ShortenerException {
		try {
			return jmp.call(Bitly.shorten(url)).getShortUrl();
		} catch (Exception e) {
			throw new ShortenerException(e);
		}
	}
}
