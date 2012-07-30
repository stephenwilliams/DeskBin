package com.alta189.hasteclient.url;

import com.alta189.hasteclient.Service;

public interface ShorteningService extends Service {
	public String shorten(String url) throws ShortenerException;

	public String expand(String url) throws ShortenerException;
}
