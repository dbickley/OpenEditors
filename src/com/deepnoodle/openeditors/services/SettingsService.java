package com.deepnoodle.openeditors.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.graphics.RGB;

import com.deepnoodle.openeditors.Constants;
import com.deepnoodle.openeditors.daos.SettingsDao;
import com.deepnoodle.openeditors.models.settings.EditorSetSettingsModel;
import com.deepnoodle.openeditors.models.settings.EditorSettingsModel;
import com.deepnoodle.openeditors.models.settings.SettingsModel;

public class SettingsService {
	// private static LogWrapper log = new LogWrapper(SettingsService.class);

	private SettingsDao settingsDao = SettingsDao.getInstance();

	private SettingsModel settings;

	private static SettingsService instance;

	public static SettingsService getInstance() {
		if (instance == null) {
			instance = new SettingsService();
		}
		return instance;
	}

	public Set<String> openWindowsSet(String fileName) {
		SettingsModel settings = getOrCreateSettings();
		return settings.getEditorSettingsSets().keySet();
	}

	// public void saveWindowSet(String setName, boolean includeInProject, IEditor[]
	// openWindows) {
	// SettingsModel settingsModel = getOrCreateSettings();
	// EditorSetSettingsModel editorSettingsSet =
	// settingsModel.getEditorSettingsSet(setName);
	// if (editorSettingsSet == null) {
	// editorSettingsSet = new EditorSetSettingsModel();
	// settingsModel.getEditorSettingsSets().put(setName, editorSettingsSet);
	// }
	// editorSettingsSet.setName(setName);
	// if (settingsModel.getActiveEditorSettingsSet() != null) {
	// editorSettingsSet.setSortBy(settingsModel.getActiveEditorSettingsSet().getSortBy());
	// } else {
	// editorSettingsSet.setSortBy(SortType.ACCESS);
	// }
	// Map<String, EditorSettingsModel> editorModels = new TreeMap<>();
	// for (IEditor editor : openWindows) {
	// editorModels.put(editor.getName(), new EditorSettingsModel(editor));
	// }
	//
	// editorSettingsSet.setEditorModels(editorModels);
	// settingsModel.setActiveSetName(setName);
	// settingsDao.saveSettings(settingsModel);
	//
	// }

	public void deleteWindowSet(String setName) {
		SettingsModel settingsModel = getOrCreateSettings();

		if (setName == settingsModel.getActiveSetName()) {
			settingsModel.setActiveSetName(Constants.OPEN_EDITORS_SET_NAME);
		}

		settingsModel.getEditorSettingsSets().remove(setName);

		settingsDao.saveSettings(settingsModel);

	}

	// TODO make private
	private SettingsModel getOrCreateSettings() {
		if (settings == null) {
			settings = settingsDao.loadSettings();
			// Create the default set name if it doesn't exist
			if (settings.keepOpenEditorsHistory()
					|| settings.getEditorSettingsSet(Constants.OPEN_EDITORS_SET_NAME) == null) {
				EditorSetSettingsModel editorSetSettingsSet = new EditorSetSettingsModel();
				editorSetSettingsSet.setSortBy(Constants.DEFAULT_SORTBY);
				settings.getEditorSettingsSets().put(Constants.OPEN_EDITORS_SET_NAME, editorSetSettingsSet);

			}
			// If the active set is null, change it to the default set
			if (!settings.stickyEditorSettings() || settings.getActiveEditorSettingsSet() == null) {
				settings.setActiveSetName(Constants.OPEN_EDITORS_SET_NAME);
			}
		}
		return settings;
	}

	public EditorSetSettingsModel copyEditorSetSettingsModel(String setName) {
		EditorSetSettingsModel existingSet = getActiveEditorSettingsSet();
		EditorSetSettingsModel newSet = new EditorSetSettingsModel();
		newSet.setName(setName);
		newSet.setSortBy(existingSet.getSortBy());

		Map<String, EditorSettingsModel> existingEditors = existingSet.getEditorModels();
		Map<String, EditorSettingsModel> newEditors = new HashMap<>();
		for (EditorSettingsModel existingEditor : existingEditors.values()) {
			EditorSettingsModel newEditor = new EditorSettingsModel(existingEditor.getFilePath(),
					existingEditor.getName());
			newEditor.setFilePath(existingEditor.getFilePath());
			newEditor.setNaturalPosition(existingEditor.getNaturalPosition());
			newEditors.put(newEditor.getFilePath(), newEditor);
		}

		newSet.setEditorModels(newEditors);

		return newSet;
	}

	public void addNewEditorSetSettingsModel(EditorSetSettingsModel newEditorSetSettingsModel) {
		getEditorSettingsSets().put(newEditorSetSettingsModel.getName(), newEditorSetSettingsModel);
		setActiveSetName(newEditorSetSettingsModel.getName());
		saveSettings();
	}

	public void saveSettings() {
		settingsDao.saveSettings(settings);
	}

	public RGB getDirtyColor() {
		return getOrCreateSettings().getDirtyColor();
	}

	public RGB getPinnedColor() {
		return getOrCreateSettings().getPinnedColor();
	}

	public RGB getHighlightColor() {
		return getOrCreateSettings().getHighlightColor();
	}

	public EditorSetSettingsModel getActiveEditorSettingsSet() {
		return getOrCreateSettings().getActiveEditorSettingsSet();
	}

	public String getActiveSetName() {
		return getOrCreateSettings().getActiveSetName();
	}

	public EditorSetSettingsModel getEditorSettingsSet(String name) {
		return getOrCreateSettings().getEditorSettingsSet(name);
	}

	public void setActiveSetName(String name) {
		getOrCreateSettings().setActiveSetName(name);

	}

	public Map<String, EditorSetSettingsModel> getEditorSettingsSets() {
		return getOrCreateSettings().getEditorSettingsSets();
	}

	public boolean keepOpenEditorsHistory() {
		return getOrCreateSettings().stickyEditorSettings();
	}

}
