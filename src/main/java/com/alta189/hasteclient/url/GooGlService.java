package com.alta189.hasteclient.url;

import net.petersson.googl.GooGl;
import net.petersson.googl.GooGlException;

import java.io.IOException;
import java.net.URL;

public class GooGlService implements ShorteningService {
	private final GooGl googl;

	public GooGlService(String apikey) {
		googl = new GooGl(apikey);
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