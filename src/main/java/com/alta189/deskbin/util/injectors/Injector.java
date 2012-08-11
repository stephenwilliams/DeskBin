package com.alta189.deskbin.util.injectors;

public interface Injector {
	public Object newInstance(Class<?> clazz);
}