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
package com.alta189.deskbin.tasks.service;

import com.alta189.deskbin.services.Service;
import com.alta189.deskbin.tasks.Task;
import com.alta189.deskbin.tasks.TaskSnapshot;

public abstract class ServiceTask<T extends Service> extends Task {
	private final T service;

	public ServiceTask(String name, T service) {
		super(name + "ServiceTask");
		this.service = service;
	}

	public ServiceTask(TaskSnapshot snapshot) {
		super(snapshot.get("name") + "ServiceTask");
		this.service = snapshot.get("service");
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