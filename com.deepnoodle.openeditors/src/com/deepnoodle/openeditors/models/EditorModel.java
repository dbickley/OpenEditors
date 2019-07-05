package com.deepnoodle.openeditors.models;

import java.util.Date;

import org.eclipse.ui.IEditorReference;

public class EditorModel {
	private String filePath;
	private String name;
	private boolean pinned;
	private Integer naturalPosition;

	//These are not saved
	private transient boolean dirty = false;
	private transient Integer historyPosition = Integer.MAX_VALUE;
	private transient IEditorReference reference;
	private transient Date lastAccessTime;

	public EditorModel(String filePath, IEditorReference reference) {
		this.filePath = filePath;
		this.reference = reference;
		name = reference.getName();
		dirty = reference.isDirty();
	}

	@Override
	public String toString() {
		return "EditorModel@" + hashCode() + "[" + name + "]";
	}

	///////////////////////////////////////
	// Getters and Setters

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String path) {
		filePath = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPinned() {
		return pinned;
	}

	public void setPinned(boolean pinned) {
		this.pinned = pinned;
	}

	public Integer getNaturalPosition() {
		return naturalPosition;
	}

	public void setNaturalPosition(Integer naturalPosition) {
		this.naturalPosition = naturalPosition;
	}

	public Integer getHistoryPosition() {
		return historyPosition;
	}

	public void setHistoryPosition(Integer historyPosition) {
		this.historyPosition = historyPosition;
	}

	public boolean isDirty() {
		return dirty;
	}

	public IEditorReference getReference() {
		return reference;
	}

	public void setReference(IEditorReference reference) {
		this.reference = reference;

	}

	public Date getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Date lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}
}
