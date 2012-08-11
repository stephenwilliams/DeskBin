package com.alta189.deskbin.service;

import com.alta189.deskbin.SnapshotConverter;
import com.alta189.deskbin.util.injectors.SnapshotInjector;

public class ServiceSnapshotConverter implements SnapshotConverter<ServiceSnapshot, Service> {
	private static final ServiceSnapshotConverter instance = new ServiceSnapshotConverter();
	
	public static ServiceSnapshotConverter getInstance() {
		return instance;
	}
	
	@Override
	public Service convert(ServiceSnapshot snapshot) {
		return new SnapshotInjector<Service, ServiceSnapshot>(snapshot).newInstance(snapshot.getSnapshotClass());
	}
}