package com.alta189.deskbin.url;

import com.alta189.deskbin.Service;

public interface ShorteningService extends Service {
	public String shorten(String url) throws ShortenerException;

	public String expand(String url) throws ShortenerException;
}
