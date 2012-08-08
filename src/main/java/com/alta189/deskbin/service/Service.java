package com.alta189.deskbin.service;

public abstract class Service {

	public Service() {
	}

	public Service(ServiceSnapshot snapshot) {
	}

	public abstract String getName();

	public abstract ServiceSnapshot generateSnapshot();
}
