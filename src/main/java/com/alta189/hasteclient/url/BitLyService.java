package com.alta189.hasteclient.url;

import com.rosaloves.bitlyj.Bitly;

public class BitLyService implements ShorteningService {
	private final Bitly.Provider bitly;

	public BitLyService(String user, String apiKey) {
		bitly = Bitly.as(user, apiKey);
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
