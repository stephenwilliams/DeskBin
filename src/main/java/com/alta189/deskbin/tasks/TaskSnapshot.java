package com.alta189.deskbin.tasks;

import com.alta189.deskbin.Snapshot;

import java.io.Serializable;

public class TaskSnapshot extends Snapshot<TaskSnapshot, Task> implements Serializable {
	private static final long serialVersionUID = 4543493514700267831L;

	public TaskSnapshot(Class<? extends Task> clazz) {
		super(clazz);
	}
}
