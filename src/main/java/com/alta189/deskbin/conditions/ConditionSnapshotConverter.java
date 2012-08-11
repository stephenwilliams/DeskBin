package com.alta189.deskbin.conditions;

import com.alta189.deskbin.SnapshotConverter;
import com.alta189.deskbin.util.injectors.SnapshotInjector;

public class ConditionSnapshotConverter implements SnapshotConverter<ConditionSnapshot, Condition> {
	private static final ConditionSnapshotConverter instance = new ConditionSnapshotConverter();

	public static ConditionSnapshotConverter getInstance() {
		return instance;
	}

	@Override
	public Condition convert(ConditionSnapshot snapshot) {
		return new SnapshotInjector<Condition, ConditionSnapshot>(snapshot).newInstance(snapshot.getSnapshotClass());
	}
}
