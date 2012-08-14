package com.alta189.deskbin.conditions;

import java.io.Serializable;

import com.alta189.deskbin.Snapshot;

public class ConditionSnapshot extends Snapshot<ConditionSnapshot, Condition> implements Serializable {
	private static final long serialVersionUID = -4221458790005253552L;

	public ConditionSnapshot(Class<? extends Condition> clazz) {
		super(clazz);
	}
}
