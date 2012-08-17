/*
 * This file is part of DeskBin.
 *
 * Copyright (c) 2012, alta189 <http://github.com/alta189/DeskBin/>
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

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.alta189.deskbin.services.image.ImageService;
import com.alta189.deskbin.services.image.ImageServiceException;
import com.alta189.deskbin.tasks.Action;
import com.alta189.deskbin.tasks.TaskSnapshot;
import com.alta189.deskbin.util.CastUtil;
import com.alta189.deskbin.util.Keyboard;

public class ImageServiceTask extends ServiceTask<ImageService> {
	public ImageServiceTask(ImageService service) {
		super(service);
	}

	public ImageServiceTask(TaskSnapshot snapshot) {
		super(snapshot);
	}

	@Override
	public void run() {
		Image image = getClipboardContents();
		try {
			String url = getService().upload(CastUtil.safeCast(image, BufferedImage.class));
			if (image != null) {
				switch (Action.getValue(getOptions().get(String.class, "action"))) {
					case WRITE:
						Keyboard.getInstance().type(url);
						notify("The image has been uploaded and the url typed.");
						break;
					case CLIPBOARD:
						Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
						StringSelection stringSelection = new StringSelection(url);
						c.setContents(stringSelection, null);
						notify("The image has been uploaded and the url put in the clipboard.");
						break;
				}
			}
		} catch (ImageServiceException e) {
			e.printStackTrace();
		}
	}

	public Image getClipboardContents() {
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);
		if (contents.isDataFlavorSupported(DataFlavor.imageFlavor)) {
			try {
				return CastUtil.safeCast(contents.getTransferData(DataFlavor.imageFlavor));
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
