package com.alta189.hasteclient.url;

public interface ShorteningService {

	public String shorten(String url) throws ShortenerException;

	public String expand(String url) throws ShortenerException;
}
