package com.alta189.deskbin.tasks;

public final class TaskRunnable implements Runnable {
	private final Task task;

	public TaskRunnable(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return task;
	}

	@Override
	public void run() {
		task.preRun();
		task.run();
		task.postRun();
	}
}
