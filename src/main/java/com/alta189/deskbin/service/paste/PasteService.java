package com.alta189.deskbin.service.paste;

import com.alta189.deskbin.service.Service;

public abstract class PasteService extends Service {

	public abstract String paste(String content) throws PasteException;

	public abstract String paste(String content, boolean isPrivate) throws PasteException;

}
