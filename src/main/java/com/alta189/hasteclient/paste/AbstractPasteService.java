package com.alta189.hasteclient.paste;

public abstract class AbstractPasteService implements PasteService {
	@Override
	public String paste(String content) throws PasteException {
		return paste(content, true);
	}
}
