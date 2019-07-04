package com.deepnoodle.openeditors.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.deepnoodle.openeditors.Activator;

public class OpenEditorsPluginPersistenceService extends FilePersistenceService {

	public OpenEditorsPluginPersistenceService() {
		String persistentDataPathLocation;
		if( Activator.getDefault() != null ) {
			persistentDataPathLocation = Activator.getDefault().getStateLocation().makeAbsolute().toOSString();
		} else {
			persistentDataPathLocation = "persistentData";
		}
		Path persistentDataPath = Paths.get( persistentDataPathLocation );
		setPersistentDataPath( persistentDataPath );
	}
}
