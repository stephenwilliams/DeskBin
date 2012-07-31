package com.alta189.deskbin.paste;

import com.alta189.deskbin.Service;

public interface PasteService extends Service {

	public String paste(String content) throws PasteException;

	public String paste(String content, boolean isPrivate) throws PasteException;

}
