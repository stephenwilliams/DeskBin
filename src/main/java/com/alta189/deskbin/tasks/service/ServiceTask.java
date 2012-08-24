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
package com.alta189.deskbin.tasks.service;

import com.alta189.deskbin.services.Service;
import com.alta189.deskbin.services.ServiceSnapshot;
import com.alta189.deskbin.services.ServiceSnapshotConverter;
import com.alta189.deskbin.tasks.NotifiableTask;
import com.alta189.deskbin.tasks.TaskSnapshot;
import com.alta189.commons.util.CastUtil;

public abstract class ServiceTask<T extends Service> extends NotifiableTask {
	private final T service;

	public ServiceTask(T service) {
		super();
		this.service = service;
	}

	public ServiceTask(TaskSnapshot snapshot) {
		super(snapshot);
		this.service = CastUtil.safeCast(ServiceSnapshotConverter.getInstance().convert(snapshot.get(ServiceSnapshot.class, "service")));
	}

	@Override
	public TaskSnapshot generateSnapshot() {
		return new TaskSnapshot(getClass())
				.add("service", service.generateSnapshot());
	}

	public T getService() {
		return service;
	}
}
