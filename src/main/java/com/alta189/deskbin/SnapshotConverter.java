package com.alta189.deskbin;

public interface SnapshotConverter<E extends Snapshot, T> {

	public T convert(E snapshot);

}
