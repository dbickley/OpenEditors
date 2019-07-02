package com.deepnoodle.openeditors.persistence;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.util.Optional;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.deepnoodle.openeditors.Activator;
import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.utils.JsonUtils;
import com.deepnoodle.openeditors.utils.PathUtils;

public class PersistenceService implements IPersistenceService {

	private static LogWrapper log = new LogWrapper(PersistenceService.class);

	@Override
	public <T> Optional<T> load(String fileName, Class<T> clazz) {
		IPath file = getPersistentDataPath(fileName);
		try {
			log.info("Loading file '" + file + "'");
			String json = Files.readString(PathUtils.toJavaPath(file), StandardCharsets.UTF_8);
			T loadedInstance = JsonUtils.fromJson(json, clazz);
			return Optional.of(loadedInstance);
		} catch (NoSuchFileException | FileNotFoundException noFileException) {
			log.info("No persisted file '" + fileName + "' has been found.");
			return Optional.empty();
		} catch (Exception e) {
			log.error(e, "Error loading file '" + fileName + "'");
			return Optional.empty();
		}
	}

	@Override
	public <T> void save(String fileName, T object) {
		IPath file = getPersistentDataPath(fileName);
		try {
			String json = JsonUtils.toJsonPrettyPrint(object);
			Files.writeString(PathUtils.toJavaPath(file), json, StandardCharsets.UTF_8);
		} catch (Exception e) {
			log.error(e, "Cannot save object " + object);
		}
	}

	private IPath getPersistentDataPath(String fileName) {
		IPath folder = getPersistentDataPath();
		IPath filePath = folder.append(fileName).makeAbsolute();
		return filePath;
	}

	private IPath getPersistentDataPath() {
		if (Activator.getDefault() != null) {
			return Activator.getDefault().getStateLocation();
		} else {
			return new Path("persistentData");
		}
	}
}
