package com.alta189.deskbin.image;

import com.alta189.deskbin.Service;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageService extends Service {

	public String upload(BufferedImage image) throws ImageServiceException;

	public String upload(List<BufferedImage> image) throws ImageServiceException;
}
