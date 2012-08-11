package com.alta189.deskbin.util.injectors;

public class EmptyInjector implements Injector {
	public static EmptyInjector instance;

	static {
		instance = new EmptyInjector();
	}

	public static EmptyInjector getInstance() {
		return instance;
	}

	private EmptyInjector() {
	}

	@Override
	public Object newInstance(Class<?> clazz) {
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			throw new InjectorException("Could not create a new instance of class '" + clazz.getCanonicalName() + "'", e.getCause());
		} catch (IllegalAccessException e) {
			throw new InjectorException("Could not create a new instance of class '" + clazz.getCanonicalName() + "'", e.getCause());
		}
	}

}