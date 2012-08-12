package com.alta189.deskbin.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class OptionsMap implements Serializable {
	private static final long serialVersionUID = -2676307273127247326L;
	private final Map<String, Object> map = new HashMap<String, Object>();

	public Map<String, Object> getMap() {
		return map;
	}

	public OptionsMap add(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public OptionsMap remove(String key) {
		map.remove(key);
		return this;
	}

	public <T> T get(Class<? extends T> clazz, String key) {
		return CastUtil.safeCast(map.get(key), clazz);
	}

	public <T> T get(Class<? extends T> clazz, String key, T defaultValue) {
		Object o = map.get(key);
		if (o != null) {
			return CastUtil.safeCast(map.get(key), clazz);
		}
		return defaultValue;
	}

	public <T> T get(String key) {
		return CastUtil.safeCast(map.get(key));
	}

	public <T> T get(String key, T defaultValue) {
		Object o = map.get(key);
		if (o != null) {
			return CastUtil.safeCast(o);
		}
		return defaultValue;
	}
}
