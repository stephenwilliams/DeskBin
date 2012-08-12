package com.alta189.deskbin.tasks.service;

import ch.swingfx.twinkle.NotificationBuilder;
import com.alta189.deskbin.services.image.ImageService;
import com.alta189.deskbin.services.image.ImageServiceException;
import com.alta189.deskbin.tasks.ServiceTask;
import com.alta189.deskbin.tasks.TaskSnapshot;
import com.alta189.deskbin.util.Keyboard;
import com.alta189.deskbin.util.OptionsMap;
import com.alta189.deskbin.util.ScreenshotUtil;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.image.BufferedImage;
import java.util.List;

public class ScreenshotImageServiceTask extends ServiceTask<ImageService> {
	private final OptionsMap options;

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
		ScreenshotType screenshotType = ScreenshotType.valueOf(options.get(String.class, "screenshot-type"));
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
			Action action = Action.valueOf(options.get(String.class, "action"));
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

	public enum Action {
		WRITE,
		CLIPBOARD;

		public static Action getValue(String str) {
			Action value = null;
			try {
				value = valueOf(str);
			} catch (Exception ignored) {
			}
			if (value == null) {
				value = WRITE;
			}
			return value;
		}
	}
}
