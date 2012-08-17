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
package com.alta189.deskbin.tasks;

import com.alta189.deskbin.SnapshotGenerator;
import com.alta189.deskbin.Snapshotable;

public abstract class Task implements Snapshotable, SnapshotGenerator<TaskSnapshot>{
	private final String taskName;

	protected Task() {
		taskName = getClass().getSimpleName();
	}

	public Task(TaskSnapshot snapshot) {
		this.taskName = snapshot.get("name");
	}

	public void preRun() {
		System.out.println("Task '" + taskName + "' starting");
	}

	public abstract void run();

	public void postRun() {
		System.out.println("Task '" + taskName + "' finished");
	}

	public final String getTaskName() {
		return taskName;
	}

	@Override
	public TaskSnapshot generateSnapshot() {
		return new TaskSnapshot(getClass())
				.add("name", taskName);
	}
}
