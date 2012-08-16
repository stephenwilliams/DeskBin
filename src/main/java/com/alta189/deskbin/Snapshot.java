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
