package com.alta189.deskbin.services.url;

import com.alta189.deskbin.services.Service;

public abstract class ShorteningService extends Service {
	public abstract String shorten(String url) throws ShortenerException;

	public abstract String expand(String url) throws ShortenerException;
}
