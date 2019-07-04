package com.deepnoodle.openeditors.persistence;

import java.util.Optional;

public interface IPersistenceService {
	<T> Optional<T> load(String name, Class<T> clazz);

	<T> void save(String name, T object);
}
