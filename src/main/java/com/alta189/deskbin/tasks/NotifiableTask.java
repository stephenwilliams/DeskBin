package com.alta189.deskbin.tasks;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.theme.DarkDefaultNotification;

public abstract class NotifiableTask extends OptionsTask {
	private static final String DEFAULT_TITLE = "DeskBin";
	private static final int DEFAULT_DISPLAY_TIME = 3000;
	private static final INotificationStyle DEFAULT_STYLE = new DarkDefaultNotification();

	protected NotifiableTask() {
		super();
	}

	public NotifiableTask(TaskSnapshot snapshot) {
		super(snapshot);
	}

	public void notify(String message) {
		if (isNotify()) {
			new NotificationBuilder()
					.withTitle(DEFAULT_TITLE)
					.withStyle(DEFAULT_STYLE)
					.withDisplayTime(getNotificationDisplayTime())
					.withMessage(message)
					.showNotification();
		}
	}

	public boolean isNotify() {
		return getOptions().get("notify", true);
	}

	public void setNotify(boolean notify) {
		getOptions().add("notify", notify);
	}

	public int getNotificationDisplayTime() {
		return getOptions().get("notification-display-time", DEFAULT_DISPLAY_TIME);
	}

	public void setNotificationDisplayTime(int displayTime) {
		getOptions().add("notification-display-time", displayTime);
	}
}
