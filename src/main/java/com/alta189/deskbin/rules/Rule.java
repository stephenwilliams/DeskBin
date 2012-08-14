package com.alta189.deskbin.rules;

import java.util.ArrayList;
import java.util.List;

import com.alta189.deskbin.SnapshotGenerator;
import com.alta189.deskbin.Snapshotable;
import com.alta189.deskbin.conditions.Condition;
import com.alta189.deskbin.conditions.ConditionSnapshot;
import com.alta189.deskbin.conditions.ConditionSnapshotConverter;
import com.alta189.deskbin.tasks.Task;
import com.alta189.deskbin.tasks.TaskRunnable;
import com.alta189.deskbin.tasks.TaskSnapshot;
import com.alta189.deskbin.tasks.TaskSnapshotConverter;

public class Rule implements Snapshotable, SnapshotGenerator<RuleSnapshot> {
	/**
	 * Conditions that must pass for the task to run
	 */
	private List<Condition> conditions;

	/**
	 * Task that is run when conditions are met
	 */
	private Task task;

	/**
	 * If true, the task will be run asynchronously
	 */
	private boolean async;

	public Rule() {
	}

	public Rule(RuleSnapshot snapshot) {
		if (snapshot != null) {
			async = snapshot.get("async", false);

			conditions = new ArrayList<Condition>();
			List<ConditionSnapshot> conditionSnapshots = new ArrayList<ConditionSnapshot>();
			for (ConditionSnapshot cs : conditionSnapshots) {
				conditions.add(ConditionSnapshotConverter.getInstance().convert(cs));
			}

			TaskSnapshot taskSnapshot = snapshot.get("task", null);
			if (taskSnapshot != null) {
				task = TaskSnapshotConverter.getInstance().convert(taskSnapshot);
			}
		}
	}

	public Rule(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public Rule(Task task) {
		this.task = task;
	}

	public Rule(List<Condition> conditions, Task task) {
		this.conditions = conditions;
		this.task = task;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public boolean isAsync() {
		return async;
	}

	public void setAsync(boolean async) {
		this.async = async;
	}

	public RuleSnapshot generateSnapshot() {
		RuleSnapshot snapshot = new RuleSnapshot(getClass());
		snapshot.add("version", 1)  // Snapshot version to be incremented when snapshot changed
				.add("async", async)
				.add("task", task.generateSnapshot());

		ArrayList<ConditionSnapshot> conditionSnapshots = new ArrayList<ConditionSnapshot>();
		for (Condition condition : conditions) {
			conditionSnapshots.add(condition.generateSnapshot());
		}

		snapshot.add("conditions", conditionSnapshots);
		return snapshot;
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

			TaskRunnable runnable = new TaskRunnable(task);

			if (async) {
				new Thread(runnable).start();
			} else {
				runnable.run();
			}
		}
	}
}
