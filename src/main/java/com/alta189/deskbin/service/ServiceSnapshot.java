package com.alta189.deskbin.service;

import com.alta189.deskbin.util.CastUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServiceSnapshot implements Serializable {
	private static final long serialVersionUID = 8133867551731195949L;
	private final Map<String, Object> map = new HashMap<String, Object>();
	private final String serviceClass;
	private transient Class<? extends Service> clazz;

	public ServiceSnapshot(Class<? extends Service> serviceClass) {
		this.serviceClass = serviceClass.getName();
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public ServiceSnapshot add(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public void remove(String key) {
		map.remove(key);
	}

	public <T> T get(String key) {
		return CastUtil.safeCast(map.get(key));
	}

	public Class<? extends Service> getServiceClass() {
		if (clazz == null) {
			try {
				Class c = Class.forName(serviceClass);
				clazz = CastUtil.safeCast(c);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return clazz;
	}
}
