package com.alta189.deskbin.services.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.List;

import com.alta189.deskbin.services.Service;
import com.alta189.deskbin.services.ServiceSnapshot;

public abstract class ImageService extends Service {

	public ImageService() {
	}

	public ImageService(ServiceSnapshot snapshot) {
		super(snapshot);
	}

	public abstract String upload(BufferedImage image) throws ImageServiceException;

	public abstract String upload(List<BufferedImage> image) throws ImageServiceException;

	public abstract String upload(File image) throws ImageServiceException;

	public abstract String upload(Collection<File> images) throws ImageServiceException;

}
