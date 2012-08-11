package com.alta189.deskbin.rules;

import com.alta189.deskbin.SnapshotConverter;
import com.alta189.deskbin.util.injectors.SnapshotInjector;

public class RuleSnapshotConverter implements SnapshotConverter<RuleSnapshot, Rule> {
	private static final RuleSnapshotConverter instance = new RuleSnapshotConverter();
	
	public static RuleSnapshotConverter getInstance() {
		return instance;
	}
	
	@Override
	public Rule convert(RuleSnapshot snapshot) {
		return new SnapshotInjector<Rule, RuleSnapshot>(snapshot).newInstance(snapshot.getSnapshotClass());
	}
}