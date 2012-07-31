package com.alta189.deskbin.util;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ScreenshotUtil {

	public static List<BufferedImage> captureEachMonitor() {
		List<BufferedImage> result = new ArrayList<BufferedImage>();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();
		for (GraphicsDevice screen : screens) {
			try {
				Robot robot = new Robot(screen);
				Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
				screenBounds.x = 0;
				screenBounds.y = 0;
				result.add(robot.createScreenCapture(screenBounds));
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static BufferedImage captureAllMonitors() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

		Rectangle allScreenBounds = new Rectangle();
		for (GraphicsDevice screen : screens) {
			Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();

			allScreenBounds.width += screenBounds.width;
			allScreenBounds.height = Math.max(allScreenBounds.height, screenBounds.height);
		}

		try {
			Robot robot = new Robot();
			return robot.createScreenCapture(allScreenBounds);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage captureCurrentMonitor() {
		Point cursor = MouseInfo.getPointerInfo().getLocation();

		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] screens = ge.getScreenDevices();

		Robot robot = null;
		Rectangle bounds = null;
		for (GraphicsDevice screen : screens) {
			bounds = screen.getDefaultConfiguration().getBounds();
			if (bounds.contains(cursor)) {
				try {
					robot = new Robot(screen);
				} catch (AWTException e) {
					e.printStackTrace();
				}
				break;
			}
		}

		if (robot != null) {
			bounds.x = 0;
			bounds.y = 0;
			return robot.createScreenCapture(bounds);
		}
		return null;
	}

	public static BufferedImage captureMainMonitor() {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		try {
			return new Robot().createScreenCapture(screenRect);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return null;
	}
}
