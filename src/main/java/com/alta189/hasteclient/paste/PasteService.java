package com.alta189.hasteclient.paste;

import com.alta189.hasteclient.Service;

public interface PasteService extends Service {

	public String paste(String content) throws PasteException;

	public String paste(String content, boolean isPrivate) throws PasteException;

}
