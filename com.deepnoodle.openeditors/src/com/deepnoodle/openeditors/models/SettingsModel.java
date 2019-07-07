package com.deepnoodle.openeditors.models;

import static com.deepnoodle.openeditors.utils.ListUtils.asList;

import java.util.List;

import com.deepnoodle.openeditors.models.EditorComparator.SortType;
import com.deepnoodle.openeditors.utils.JsonUtils;

public class SettingsModel {

	private List<SortType> sortSequence =
	    asList( SortType.PATH, SortType.NAME, SortType.EXTENSION, SortType.NATURAL, SortType.ACCESS );

	public SettingsModel() {
	}

	@Override
	public String toString() {
		return JsonUtils.toJson( this );
	}

	///////////////////////////////////////
	// Getters and Setters

	public List<SortType> getSortSequence() {
		return sortSequence;
	}

	public void setSortSequence(List<SortType> sortSequence) {
		this.sortSequence = sortSequence;
	}
}
