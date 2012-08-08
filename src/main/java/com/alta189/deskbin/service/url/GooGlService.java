package com.alta189.deskbin.service.url;

import com.alta189.deskbin.service.ServiceSnapshot;
import net.petersson.googl.GooGl;
import net.petersson.googl.GooGlException;

import java.io.IOException;
import java.net.URL;

public class GooGlService extends ShorteningService {
	private static final String NAME = "goo.gl";
	private final GooGl googl;

	public GooGlService(String apikey) {
		googl = new GooGl(apikey);
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
