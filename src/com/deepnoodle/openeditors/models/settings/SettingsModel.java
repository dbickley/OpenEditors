package com.deepnoodle.openeditors.models.settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.graphics.RGB;

import com.deepnoodle.openeditors.Constants;

public class SettingsModel {

	//TODO extract away eclipse reference, store as separate values or hex
	private RGB highlightColor = new RGB(219, 219, 219);
	private RGB pinnedColor = new RGB(60, 15, 175);
	private RGB dirtyColor = new RGB(204, 0, 0);
	private RGB closedColor = new RGB(130, 130, 130);

	private String activeSetName = Constants.OPEN_EDITORS_SET_NAME;

	private Map<String, EditorSetSettingsModel> editorSettingsSets = new HashMap<>();

	public SettingsModel() {
		if (editorSettingsSets.get(activeSetName) == null) {
			editorSettingsSets.put(activeSetName, new EditorSetSettingsModel());
		}
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

	public String getActiveSetName() {
		return activeSetName;
	}

	public void setActiveSetName(String currentSetName) {
		activeSetName = currentSetName;
	}

	public EditorSetSettingsModel getActiveEditorSettingsSet() {
		return getEditorSettingsSets().get(getActiveSetName());
	}

	public Map<String, EditorSetSettingsModel> getEditorSettingsSets() {
		return editorSettingsSets;
	}

	public void setEditorSettingsSets(Map<String, EditorSetSettingsModel> editorSettingsSets) {
		this.editorSettingsSets = editorSettingsSets;
	}

	public EditorSetSettingsModel getEditorSettingsSet(String name) {
		return getEditorSettingsSets().get(name);
	}

	public Set<String> getSets() {
		return getEditorSettingsSets().keySet();
	}

	//TODO add to settings
	public boolean keepOpenEditorsHistory() {
		return true;
	}

	//TODO add to settings
	public boolean stickyEditorSettings() {
		return false;
	}

}
