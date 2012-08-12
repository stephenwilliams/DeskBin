package com.alta189.deskbin.tasks;

import com.alta189.deskbin.services.Service;

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
