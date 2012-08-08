package com.alta189.deskbin.service.paste;

public abstract class AbstractPasteService extends PasteService {

	@Override
	public String paste(String content) throws PasteException {
		return paste(content, true);
	}
}
