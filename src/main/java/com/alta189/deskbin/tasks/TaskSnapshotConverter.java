package com.alta189.deskbin.tasks;

import com.alta189.deskbin.SnapshotConverter;
import com.alta189.deskbin.util.injectors.SnapshotInjector;

public class TaskSnapshotConverter implements SnapshotConverter<TaskSnapshot, Task> {
	private static final TaskSnapshotConverter instance = new TaskSnapshotConverter();
	
	public static TaskSnapshotConverter getInstance() {
		return instance;
	}
	
	@Override
	public Task convert(TaskSnapshot snapshot) {
		return new SnapshotInjector<Task, TaskSnapshot>(snapshot).newInstance(snapshot.getSnapshotClass());
	}
}