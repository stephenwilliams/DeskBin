package com.alta189.deskbin.service;

import com.alta189.deskbin.SnapshotGenerator;
import com.alta189.deskbin.Snapshotable;

public abstract class Service implements Snapshotable, SnapshotGenerator<ServiceSnapshot> {

	public Service() {
	}

	public Service(ServiceSnapshot snapshot) {
	}

	public abstract String getName();

}
