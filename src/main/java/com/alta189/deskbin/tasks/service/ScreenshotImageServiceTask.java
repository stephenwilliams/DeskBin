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

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.List;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;
import com.alta189.deskbin.services.image.ImageService;
import com.alta189.deskbin.services.image.ImageServiceException;
import com.alta189.deskbin.tasks.Action;
import com.alta189.deskbin.tasks.ServiceTask;
import com.alta189.deskbin.tasks.TaskSnapshot;
import com.alta189.deskbin.util.Keyboard;
import com.alta189.deskbin.util.OptionsMap;
import com.alta189.deskbin.util.ScreenshotUtil;

public class ScreenshotImageServiceTask extends ServiceTask<ImageService> {
	private final OptionsMap options;

	public ScreenshotImageServiceTask(ImageService service) {
		this(service, new OptionsMap());
	}

	public ScreenshotImageServiceTask(ImageService service, OptionsMap options) {
		super("ScreenshotImage", service);
		this.options = options;
	}

	public ScreenshotImageServiceTask(TaskSnapshot snapshot) {
		super(snapshot);
		this.options = snapshot.get("options", new OptionsMap());
	}

	@Override
	public TaskSnapshot generateSnapshot() {
		return super.generateSnapshot()
				.add("version", 1)
				.add("options", options);
	}

	@Override
	public void run() {
		ScreenshotType screenshotType = ScreenshotType.getValue(options.get(String.class, "screenshot-type"));
		String url = null;
		switch (screenshotType) {
			case MAIN_MONITOR:
				url = upload(ScreenshotUtil.captureMainMonitor());
				break;
			case CURRENT_MONITOR:
				url = upload(ScreenshotUtil.captureCurrentMonitor());
				break;
			case ALL_MONITORS:
				url = upload(ScreenshotUtil.captureAllMonitors());
				break;
			case EACH_MONITOR:
				url = upload(ScreenshotUtil.captureEachMonitor());
				break;
		}
		if (url != null) {
			Action action = Action.getValue(options.get(String.class, "action"));
			switch (action) {
				case WRITE:
					Keyboard.getInstance().type(url);
					notify("Screenshot taken and upload url(s) typed.");
					break;
				case CLIPBOARD:
					Keyboard.getInstance();
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					Transferable transferable = new StringSelection(url);
					clipboard.setContents(transferable, null);
					notify("Screenshot taken and upload url(s) copied to the clipboard.");
					break;
			}
		}
	}

	private void notify(String message) {
		boolean notify = options.get("notify", true);
		if (notify) {
			int displayTime = options.get("notification-display-time", 3000);
			new NotificationBuilder()
					.withTitle("DeskBin")
					.withStyle(new DarkDefaultNotification())
					.withDisplayTime(displayTime)
					.withMessage(message)
					.showNotification();
		}
	}

	private String upload(BufferedImage image) {
		try {
			return getService().upload(image);
		} catch (ImageServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String upload(List<BufferedImage> images) {
		try {
			return getService().upload(images);
		} catch (ImageServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	public enum ScreenshotType {
		MAIN_MONITOR,
		CURRENT_MONITOR, // default
		ALL_MONITORS,
		EACH_MONITOR;

		public static ScreenshotType getValue(String str) {
			ScreenshotType value = null;
			try {
				value = valueOf(str);
			} catch (Exception ignored) {
			}
			if (value == null) {
				value = CURRENT_MONITOR;
			}
			return value;
		}
	}
}
