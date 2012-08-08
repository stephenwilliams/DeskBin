package com.alta189.deskbin.service.url;

import com.alta189.deskbin.service.ServiceSnapshot;
import com.rosaloves.bitlyj.Bitly;

public class BitLyService extends ShorteningService {
	private static final String NAME = "bit.ly";
	private final Bitly.Provider bitly;
	private final String user;

	public BitLyService(String user, String apiKey) {
		bitly = Bitly.as(user, apiKey);
		this.user = user;
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
}
