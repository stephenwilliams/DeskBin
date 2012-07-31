package com.alta189.deskbin.paste;

public abstract class AbstractPasteService implements PasteService {
	@Override
	public String paste(String content) throws PasteException {
		return paste(content, true);
	}
}
