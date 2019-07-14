package com.deepnoodle.openeditors.persistence;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Optional;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.utils.JsonUtils;

public class FilePersistenceService implements IPersistenceService {

	private static LogWrapper log = new LogWrapper( FilePersistenceService.class );

	private java.nio.file.Path persistentDataPath;

	public FilePersistenceService(Path persistentDataPath) {
		this.persistentDataPath = persistentDataPath;
	}

	/**
	 * Constructor for subclasses only.
	 * Calling subclasses must set the persistentDataPath afterwards.
	 */
	protected FilePersistenceService() {
	}

	@Override
	public <T> Optional<T> load(String fileName, Class<T> clazz) {
		Path filePath = getPersistentFilePath( fileName );
		try {
			//			log.info( "Reading file '" + filePath + "'" );
			String json = Files.readString( filePath, StandardCharsets.UTF_8 );
			T loadedInstance = JsonUtils.fromJson( json, clazz );
			return Optional.of( loadedInstance );
		} catch( NoSuchFileException | FileNotFoundException noFileException ) {
			log.info( "No persisted file '" + fileName + "' has been found." );
			return Optional.empty();
		} catch( Exception e ) {
			log.error( e, "Error loading file '" + fileName + "'" );
			return Optional.empty();
		}
	}

	@Override
	public <T> void save(String fileName, T object) {
		Path filePath = getPersistentFilePath( fileName );
		try {
			//			log.info( "Writing file '" + filePath + "'" );
			String json = JsonUtils.toJsonPrettyPrint( object );
			Files.writeString( filePath, json, StandardCharsets.UTF_8 );
		} catch( Exception e ) {
			log.error( e, "Cannot save object " + object );
		}
	}

	public Path getPersistentFilePath(String fileName) {
		return persistentDataPath.resolve( fileName );
	}

	public void setPersistentDataPath(Path persistentDataPath) {
		this.persistentDataPath = persistentDataPath;
	}

	public Path getPersistentDataPath() {
		return persistentDataPath;
	}
}
