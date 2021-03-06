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
package com.alta189.deskbin.conditions;

import com.alta189.deskbin.SnapshotConverter;
import com.alta189.deskbin.util.injectors.SnapshotInjector;

public class ConditionSnapshotConverter implements SnapshotConverter<ConditionSnapshot, Condition> {
	private static final ConditionSnapshotConverter instance = new ConditionSnapshotConverter();

	public static ConditionSnapshotConverter getInstance() {
		return instance;
	}

	@Override
	public Condition convert(ConditionSnapshot snapshot) {
		return new SnapshotInjector<Condition, ConditionSnapshot>(snapshot).newInstance(snapshot.getSnapshotClass());
	}
}
