package com.deepnoodle.openeditors.models;

import java.util.Date;

import org.eclipse.ui.IEditorReference;

import com.deepnoodle.openeditors.utils.FileUtils;
import com.deepnoodle.openeditors.utils.StringUtils;

public class EditorModel {
	private IEditorReference reference;
	private String name;

	private String filePath;
	private boolean dirty;
	private Integer naturalPosition;

	private boolean pinned;
	private Date lastAccessTime;

	public EditorModel(IEditorReference reference) {
		this.reference = reference;
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

	public String getFileExtension() {
		if( !StringUtils.isNullOrEmpty( name ) ) {
			return FileUtils.getFileExtension( name );
		} else {
			return "";
		}
	}
}
