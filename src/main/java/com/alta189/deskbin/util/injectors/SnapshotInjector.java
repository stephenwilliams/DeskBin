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
package com.alta189.deskbin.util.injectors;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.alta189.commons.util.injectors.InjectorException;
import com.alta189.deskbin.Snapshot;
import com.alta189.deskbin.Snapshotable;
import com.alta189.commons.util.CastUtil;

public class SnapshotInjector<T extends Snapshotable, E extends Snapshot<E, ? extends T>> {
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
