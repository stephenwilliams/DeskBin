package com.alta189.hasteclient.paste;

public class PasteException extends Exception {
	public PasteException(String message) {
		super(message);
	}

	public PasteException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasteException(Throwable cause) {
		super(cause);
	}
}
