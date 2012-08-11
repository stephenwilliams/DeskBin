package com.alta189.deskbin.conditions;

import com.alta189.deskbin.Snapshot;

import java.io.Serializable;

public class ConditionSnapshot extends Snapshot<Condition> implements Serializable {
	private static final long serialVersionUID = -4221458790005253552L;

	public ConditionSnapshot(Class<? extends Condition> clazz) {
		super(clazz);
	}
}
