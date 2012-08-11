package com.alta189.deskbin.services.paste;

import com.alta189.deskbin.services.Service;

public abstract class PasteService extends Service {

	public abstract String paste(String content) throws PasteException;

	public abstract String paste(String content, boolean isPrivate) throws PasteException;

}
