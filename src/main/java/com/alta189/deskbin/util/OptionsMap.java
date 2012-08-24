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
package com.alta189.deskbin.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.alta189.commons.util.CastUtil;

public class OptionsMap implements Serializable {
	private static final long serialVersionUID = -2676307273127247326L;
	private final Map<String, Object> map = new HashMap<String, Object>();

	public Map<String, Object> getMap() {
		return map;
	}

	public OptionsMap put(String key, Object value) {
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
