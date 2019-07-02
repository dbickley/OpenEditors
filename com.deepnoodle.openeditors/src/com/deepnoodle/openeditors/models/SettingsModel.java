package com.deepnoodle.openeditors.models;

import org.eclipse.swt.graphics.RGB;

import com.deepnoodle.openeditors.models.EditorComparator.SortType;
import com.deepnoodle.openeditors.utils.JsonUtils;

public class SettingsModel {

	private RGB highlightColor = new RGB(219, 219, 219);
	private RGB pinnedColor = new RGB(60, 15, 175);
	private RGB dirtyColor = new RGB(204, 0, 0);

	private SortType sortBy = SortType.ACCESS;

	public SettingsModel() {
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}

	///////////////////////////////////////
	// Getters and Setters

	public RGB getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(RGB highlightColor) {
		this.highlightColor = highlightColor;
	}

	public RGB getPinnedColor() {
		return pinnedColor;
	}

	public void setPinnedColor(RGB pinnedColor) {
		this.pinnedColor = pinnedColor;
	}

	public RGB getDirtyColor() {
		return dirtyColor;
	}

	public void setDirtyColor(RGB dirtyColor) {
		this.dirtyColor = dirtyColor;
	}

	public SortType getSortBy() {
		return sortBy;
	}

	public void setSortBy(SortType sortBy) {
		this.sortBy = sortBy;
	}
}
