package com.deepnoodle.openeditors.daos;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;

import com.deepnoodle.openeditors.Activator;
import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.settings.SettingsModel;
import com.google.gson.GsonBuilder;

public class SettingsDao {
	private static LogWrapper log = new LogWrapper(SettingsDao.class);

	private static final String SETS_FILE_NAME = "settings.deepnoodle";
	private static final String SETS_ROOT = "OpenEditor";

	private static SettingsDao instance;

	public static SettingsDao getInstance() {
		if (instance == null) {
			instance = new SettingsDao();
		}
		return instance;
	}

	public SettingsModel loadSettings() {
		IPath path = Activator.getDefault().getStateLocation();
		String settingsFileName = getSettingsPath(path);
		DialogSettings settings = new DialogSettings(SETS_ROOT);
		try {
			settings.load(settingsFileName);

			IDialogSettings settingsModelJsonSection = DialogSettings.getOrCreateSection(settings, "SettingsEntity");
			String json = settingsModelJsonSection.get("json");
			if (json != null) {
				return new GsonBuilder().create().fromJson(json, SettingsModel.class);
			}
		} catch (FileNotFoundException e) {
			log.info("Settings can not be loaded (IOException), probably because none have been saved yet");
		} catch (IOException e) {
			log.warn(e);
		}
		return new SettingsModel();
	}

	public void saveSettings(SettingsModel settingsModel) {
		//TODO add back in once sets are activated
		//		try {
		//			DialogSettings settings = new DialogSettings(SETS_ROOT);
		//			DialogSettings.getOrCreateSection(settings, "SettingsEntity");
		//			IDialogSettings settingsModelJsonSection = DialogSettings.getOrCreateSection(settings, "SettingsEntity");
		//
		//			String json = new GsonBuilder().create().toJson(settingsModel);
		//			settingsModelJsonSection.put("json", json);
		//
		//			IPath path = Activator.getDefault().getStateLocation();
		//			String settingsFileName = getSettingsPath(path);
		//			settings.save(settingsFileName);
		//		} catch (IOException e) {
		//			log.warn(e);
		//		}

	}

	public String getSettingsPath(IPath path) {
		String settingsFileName = path.append(SETS_FILE_NAME).toOSString();
		return settingsFileName;
	}
}
