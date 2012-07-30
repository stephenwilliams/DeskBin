package com.alta189.hasteclient.url;

import com.rosaloves.bitlyj.Bitly;
import com.rosaloves.bitlyj.Jmp;

public class JmpService implements ShorteningService {
	private Bitly.Provider jmp;

	public JmpService(String user, String apiKey) {
		jmp = Jmp.as(user, apiKey);
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
