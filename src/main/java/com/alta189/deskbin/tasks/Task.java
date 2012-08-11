package com.alta189.deskbin.tasks;

import com.alta189.deskbin.SnapshotGenerator;
import com.alta189.deskbin.Snapshotable;

public abstract class Task implements Snapshotable, SnapshotGenerator<TaskSnapshot>{
	private final String taskName;

	protected Task(String taskName) {
		this.taskName = taskName;
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

}
