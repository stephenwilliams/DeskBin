/*
 * This file is part of DeskBin.
 *
 * Copyright (c) ${project.inceptionYear}-2012, alta189 <http://github.com/alta189/DeskBin/>
 * DeskBin is licensed under the GNU Lesser General Public License.
 *
 * DeskBin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DeskBin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.alta189.deskbin.tasks.service;

import ch.swingfx.twinkle.NotificationBuilder;
import com.alta189.deskbin.services.image.ImageService;
import com.alta189.deskbin.services.image.ImageServiceException;
import com.alta189.deskbin.tasks.Action;
import com.alta189.deskbin.tasks.ServiceTask;
import com.alta189.deskbin.tasks.TaskSnapshot;
import com.alta189.deskbin.util.CastUtil;
import com.alta189.deskbin.util.Keyboard;
import com.alta189.deskbin.util.OptionsMap;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageFileServiceTask extends ServiceTask<ImageService> {
	private final OptionsMap options;

	public ImageFileServiceTask(ImageService service) {
		this(service, new OptionsMap());
	}

	public ImageFileServiceTask(ImageService service, OptionsMap options) {
		super("ImageFile", service);
		this.options = options;
	}

	public ImageFileServiceTask(TaskSnapshot snapshot) {
		super(snapshot);
		this.options = snapshot.get("options", new OptionsMap());
	}

	@Override
	public void run() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		if (contents != null && contents.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			try {
				List<File> files = CastUtil.safeCast(contents.getTransferData(DataFlavor.javaFileListFlavor));
				if (files != null && files.size() > 0) {
					for (File file : files) {
						if (!isImageFile(file)) {
							files.remove(file);
						}
					}

					if (files.isEmpty()) {
						return;
					}

					try {
						String urls = getService().upload(files);
						boolean one = files.size() == 1;
						switch (Action.getValue(options.get(String.class, "action"))) {
							case WRITE:
								notify(String.format("The image file%s been typed.", one ? "'s upload url has" : "s' upload urls have"));
								Keyboard.getInstance().type(urls);
								break;
							case CLIPBOARD:
								notify("The image file" + (one ? "'s upload url has" : "s' upload urls have") + " been put in the clipboard.");
								break;
						}
					} catch (ImageServiceException e) {
						e.printStackTrace();
					}
				}
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean isImageFile(File file) {
		String extension = getExtension(file);
		if (extension != null && !extension.isEmpty()) {
			 ImageFileTypes imt = ImageFileTypes.getValue(extension);
			if (imt != null) {
				return true;
			}
		}
		return false;
	}

	public String getExtension(File file) {
		String extension = null;
		String name = file.getName();
		int i = name.lastIndexOf(".");

		if (i > 0 && i < name.length() - 1) {
			extension = name.substring(i + 1).toLowerCase();
		}

		if (extension == null) {
			return "";
		}
		return extension;
	}

	private void notify(String message) {
		boolean notify = options.get("notify", true);
		if (notify) {
			int displayTime = options.get("notification-display-time", 3000);
			new NotificationBuilder()
					.withTitle("DeskBin")
					.withDisplayTime(displayTime)
					.withMessage(message)
					.showNotification();
		}
	}

	public enum ImageFileTypes {
		PNG,
		JPEG,
		GIF,
		BMP;

		public static ImageFileTypes getValue(String str) {
			ImageFileTypes value = null;
			try {
				value = valueOf(str);
			} catch (Exception ignored) {
			}
			return value;
		}
	}
}
