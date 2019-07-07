package com.deepnoodle.openeditors.utils;

import java.io.File;

public final class FileUtils {
	private FileUtils() {
	}

	public static String getFileExtension(String filePath) {
		String fileName = new File( filePath ).getName();
		int dotIndex = fileName.lastIndexOf( '.' );
		return ( dotIndex == -1 ) ? "" : fileName.substring( dotIndex + 1 );
	}
}
