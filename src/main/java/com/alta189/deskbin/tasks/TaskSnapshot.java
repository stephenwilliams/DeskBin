package com.alta189.deskbin.tasks;

import java.io.Serializable;

import com.alta189.deskbin.Snapshot;

public class TaskSnapshot extends Snapshot<TaskSnapshot, Task> implements Serializable {
	private static final long serialVersionUID = 4543493514700267831L;

	public TaskSnapshot(Class<? extends Task> clazz) {
		super(clazz);
	}
}
