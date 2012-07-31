package com.alta189.deskbin.image;

public class ImageServiceException extends Exception {
	public ImageServiceException(String message) {
		super(message);
	}

	public ImageServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ImageServiceException(Throwable cause) {
		super(cause);
	}
}
