package com.alta189.deskbin.util;

public class CastUtil {

	public static <T> T safeCast(Object object) {
		try {
			return (T) object;
		} catch (ClassCastException ignored) {
		}
		return null;
	}

	public static <T> T safeCast(Object object, Class<T> type) {
		try {
			return (T) object;
		} catch (ClassCastException ignored) {
		}
		return null;
	}

}