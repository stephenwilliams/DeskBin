package com.alta189.deskbin;

public interface SnapshotGenerator<T extends Snapshot> {

	public T generateSnapshot();

}
