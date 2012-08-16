package com.alta189.deskbin.tasks;

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