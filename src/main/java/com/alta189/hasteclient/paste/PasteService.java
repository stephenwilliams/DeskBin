package com.alta189.hasteclient.paste;

public interface PasteService {

	public String paste(String content) throws PasteException;

	public String paste(String content, boolean isPrivate) throws PasteException;

}
