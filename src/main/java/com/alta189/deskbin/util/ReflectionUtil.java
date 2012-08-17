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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil {
	public static Field findField(Class<?> clazz, String field) {
		Field result;
		Class<?> search = clazz;
		while (search != null) {
			try {
				result = clazz.getDeclaredField(field);
				return result;
			} catch (NoSuchFieldException ignored) {
			}
			search = search.getSuperclass();
		}
		return null;
	}

	public static <T> T getFieldValue(Object target, String name) {
		return getFieldValue(target.getClass(), target, name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Class<?> clazz, Object target, String name) {
		Field field = findField(clazz, name);
		if (field != null) {
			field.setAccessible(true);
			try {
				return (T) field.get(target);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void setFieldValue(Object target, String name, Object value) {
		Field field = findField(target.getClass(), name);
		if (field != null) {
			field.setAccessible(true);
			try {
				field.set(target, value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class[0]);
	}

	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		return findMethod(clazz, paramTypes, name);
	}

	public static Method findMethod(Class<?> clazz, Class<?>[] paramTypes, String name) {
		Class<?> search = clazz;
		while (search != null) {
			Method[] methods = (search.isInterface() ? search.getMethods() : search.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName()) && (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			search = search.getSuperclass();
		}
		return null;
	}

	public static <T> T invokeMethod(String name, Object target) {
		Method method = findMethod(target.getClass(), name);
		if (method == null) {
			return null;
		}
		return invokeMethod(method, target);
	}

	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	public static <T> T invokeMethod(String name, Object target, Object... args) {
		List<Class<?>> list = new ArrayList<Class<?>>();
		for (Object arg : args) {
			list.add(arg.getClass());
		}
		Method method = findMethod(target.getClass(), list.toArray(new Class<?>[0]), name);
		if (method == null) {
			return null;
		}
		return invokeMethod(method, target);
	}

	@SuppressWarnings("unchecked")
	public static <T> T invokeMethod(Method method, Object target, Object... args) {
		try {
			return (T) method.invoke(target, args);
		} catch (IllegalAccessException ignored) {
		} catch (InvocationTargetException ignored) {
		} catch (ClassCastException ignored) {
		}
		return null;
	}
}
