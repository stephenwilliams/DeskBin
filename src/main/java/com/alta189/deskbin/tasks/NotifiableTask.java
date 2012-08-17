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
		getOptions().put("notify", notify);
	}

	public int getNotificationDisplayTime() {
		return getOptions().get("notification-display-time", DEFAULT_DISPLAY_TIME);
	}

	public void setNotificationDisplayTime(int displayTime) {
		getOptions().put("notification-display-time", displayTime);
	}
}
