package com.deepnoodle.openeditors.utils;

public class IndexedEntry<T> {
	T entry;
	int index;

	IndexedEntry(T entry, int index) {
		this.entry = entry;
		this.index = index;
	}

	public T getEntry() {
		return entry;
	}

	public int getIndex() {
		return index;
	}
}
