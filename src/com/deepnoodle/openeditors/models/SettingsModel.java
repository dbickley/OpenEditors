package com.deepnoodle.openeditors.models;

import org.eclipse.swt.graphics.RGB;

import com.deepnoodle.openeditors.models.EditorComparator.SortType;

public class SettingsModel {

	//TODO extract away eclipse reference, store as separate values or hex
	private RGB highlightColor = new RGB(219, 219, 219);
	private RGB pinnedColor = new RGB(60, 15, 175);
	private RGB dirtyColor = new RGB(204, 0, 0);
	private RGB closedColor = new RGB(130, 130, 130);

	private SortType sortBy = SortType.ACCESS;

	public SettingsModel() {
	}

	public RGB getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(RGB highlightColor) {
		this.highlightColor = highlightColor;
	}
	
	public RGB getClosedColor() {
		return closedColor;
	}
	
	public void setClosedColor(RGB closedColor) {
		this.closedColor = closedColor;
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
