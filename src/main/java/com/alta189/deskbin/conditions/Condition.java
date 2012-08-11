package com.alta189.deskbin.conditions;

import com.alta189.deskbin.SnapshotGenerator;
import com.alta189.deskbin.Snapshotable;

public abstract class Condition implements Snapshotable, SnapshotGenerator<ConditionSnapshot> {

	public abstract boolean passes();

}
