package com.deepnoodle.openeditors.services;

import java.util.Optional;
import java.util.function.Consumer;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.SettingsModel;
import com.deepnoodle.openeditors.persistence.IPersistenceService;
import com.deepnoodle.openeditors.persistence.OpenEditorsPluginPersistenceService;

/**
 * Queries, updates and persists settings.
 * @author andre
 *
 */
public class SettingsService {
	private static LogWrapper log = new LogWrapper( SettingsService.class );

	private static final String SETTINGS_FILENAME = "settings.json";

	private IPersistenceService persistenceService = new OpenEditorsPluginPersistenceService();
	private SettingsModel settings;

	/**
	 * Returns the current settings model. It will be loaded from the file system if not done yet.
	 * If there is no persisted settings model that can be loaded,
	 * then a new default settings model is returned.
	 * @return
	 */
	public SettingsModel getSettings() {
		if( settings == null ) {
			Optional<SettingsModel> loadedSettings = persistenceService.load( SETTINGS_FILENAME, SettingsModel.class );
			settings = loadedSettings.orElseGet( () -> {
				log.info( "Loaded default settings model" );
				return new SettingsModel();
			} );
			log.info( "Loaded settings model " + settings );
		}
		return settings;
	}

	/**
	 * Saves the current settings model to the file system.
	 */
	public void saveSettings() {
		log.info( "Saving settings model" );
		persistenceService.save( SETTINGS_FILENAME, settings );
	}

	/**
	 * Runs the consumer on the current settings model (e.g. to call its setters)
	 * and saves the settings model afterwards.
	 */
	public void editAndSave(Consumer<SettingsModel> consumer) {
		consumer.accept( getSettings() );
		log.info( "Changed settings model: " + getSettings() );
		saveSettings();
	}
}
