package com.alta189.deskbin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alta189.deskbin.util.CastUtil;

public abstract class Snapshot<S, E> implements Serializable {
	private static final long serialVersionUID = 81632039381923753L;
	private final Map<String, Object> map = new HashMap<String, Object>();
	private final String className;
	private transient Class<? extends E> clazz;

	public Snapshot(Class<? extends E> clazz) {
		this.className = clazz.getName();
		this.clazz = clazz;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public S add(String key, Object value) {
		map.put(key, value);
		return self();
	}

	public S remove(String key) {
		map.remove(key);
		return self();
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

	public Class<? extends E> getSnapshotClass() {
		if (clazz == null) {
			try {
				Class c = Class.forName(className);
				clazz = CastUtil.safeCast(c);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return clazz;
	}

	private final S self() {
		return CastUtil.safeCast(this);
	}
}
