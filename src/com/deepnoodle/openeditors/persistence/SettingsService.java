package com.deepnoodle.openeditors.persistence;

import org.eclipse.swt.graphics.RGB;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorComparator.SortType;
import com.deepnoodle.openeditors.models.SettingsModel;

public class SettingsService {
	private static LogWrapper log = new LogWrapper(SettingsService.class);

	private SettingsDao settingsDao = SettingsDao.getInstance();

	private SettingsModel settings;

	private static SettingsService instance;

	public static SettingsService getInstance() {
		if (instance == null) {
			instance = new SettingsService();
		}
		return instance;
	}

	private SettingsModel getOrCreateSettings() {
		if (settings == null) {
			settings = settingsDao.loadSettings();
		}
		return settings;
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

	public RGB getClosedColor() {
		return getOrCreateSettings().getClosedColor();
	}
	
	public SortType getSortBy() {
		return getOrCreateSettings().getSortBy();
	}

	public void setSortBy(SortType sortBy) {
		getOrCreateSettings().setSortBy(sortBy);
	}

}
