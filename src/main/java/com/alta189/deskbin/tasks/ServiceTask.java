package com.alta189.deskbin.tasks;

public class ServiceTask extends Task {
	protected ServiceTask() {
		super("ServiceTask");
	}

	@Override
	public void run() {
	}

	@Override
	public TaskSnapshot generateSnapshot() {
		return null;
	}
}
