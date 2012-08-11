package com.alta189.deskbin.util.injectors;

import com.alta189.deskbin.Snapshot;
import com.alta189.deskbin.Snapshotable;
import com.alta189.deskbin.util.CastUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SnapshotInjector<T extends Snapshotable, E extends Snapshot<? extends T>> {
	private final E snapshot;

	public SnapshotInjector(E snapshot) {
		this.snapshot = snapshot;
	}

	public T newInstance(Class<? extends T> clazz) {
		try {
			Constructor constructor = clazz.getConstructor(snapshot.getClass());
			return CastUtil.safeCast(constructor.newInstance(snapshot));
		} catch (NoSuchMethodException e) {
			throw new InjectorException(e);
		} catch (InvocationTargetException e) {
			throw new InjectorException(e);
		} catch (InstantiationException e) {
			throw new InjectorException(e);
		} catch (IllegalAccessException e) {
			throw new InjectorException(e);
		}
	}
}
