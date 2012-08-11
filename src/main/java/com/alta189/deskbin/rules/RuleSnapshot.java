package com.alta189.deskbin.rules;

import com.alta189.deskbin.Snapshot;

import java.io.Serializable;

public class RuleSnapshot extends Snapshot<Rule> implements Serializable {
	private static final long serialVersionUID = 6817388686458719376L;

	public RuleSnapshot(Class<? extends Rule> clazz) {
		super(clazz);
	}
}
