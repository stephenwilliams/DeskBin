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
