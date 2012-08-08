package com.alta189.deskbin.rules;

import java.util.List;

public class Rule {
	/**
	 * Conditions that must pass for the task to run
	 */
	private List<Condition> conditions;
	/**
	 * Task that is run when conditions are met
	 */
	private Runnable task;
	/**
	 * If true, the task will be run asynchronously
	 */
	private boolean async;

	public Rule() {
	}

	public Rule(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public Rule(Runnable task) {
		this.task = task;
	}

	public Rule(List<Condition> conditions, Runnable task) {
		this.conditions = conditions;
		this.task = task;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public Runnable getTask() {
		return task;
	}

	public void setTask(Runnable task) {
		this.task = task;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public void execute() {
		if (task != null && conditions != null) {
			if (conditions.size() > 0) {
				for (Condition condition : conditions) {
					if (!condition.passes()) {
						return;
					}
				}
			}

			if (async) {
				new Thread(task).start();
			} else {
				task.run();
			}
		}
	}
}
