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
