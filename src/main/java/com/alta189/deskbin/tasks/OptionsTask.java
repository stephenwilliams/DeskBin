package com.alta189.deskbin.tasks;

import com.alta189.deskbin.util.OptionsMap;

public abstract class OptionsTask extends Task {
	private OptionsMap options;

	protected OptionsTask() {
		super();
	}

	public OptionsTask(TaskSnapshot snapshot) {
		super(snapshot);
		options = snapshot.get("options", new OptionsMap());
	}

	public OptionsMap getOptions() {
		return options;
	}

	public void setOptions(OptionsMap options) {
		this.options = options;
	}

	@Override
	public TaskSnapshot generateSnapshot() {
		return super.generateSnapshot()
				.add("options", options);
	}
}
