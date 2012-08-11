package com.alta189.deskbin.services;

import com.alta189.deskbin.Snapshot;

import java.io.Serializable;

public class ServiceSnapshot extends Snapshot<ServiceSnapshot, Service> implements Serializable {
	private static final long serialVersionUID = 8133867551731195949L;

	public ServiceSnapshot(Class<? extends Service> clazz) {
		super(clazz);
	}
}
