package com.alta189.deskbin.service;

import com.alta189.deskbin.Snapshot;

import java.io.Serializable;

public class ServiceSnapshot extends Snapshot<Service> implements Serializable {
	private static final long serialVersionUID = 8133867551731195949L;

	public ServiceSnapshot(Class<? extends Service> clazz) {
		super(clazz);
	}
}
