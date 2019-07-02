package com.deepnoodle.openeditors.persistence;

import java.util.Optional;

public interface IPersistenceService {
	<T> Optional<T> load(String fileName, Class<T> clazz);

	<T> void save(String fileName, T object);
}
