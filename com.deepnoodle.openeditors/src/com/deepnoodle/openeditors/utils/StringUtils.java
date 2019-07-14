package com.deepnoodle.openeditors.utils;

public final class StringUtils {

	private StringUtils() {
	}

	public static boolean isNullOrEmpty(CharSequence s) {
		return ( s == null || s.length() == 0 );
	}

	public static String nullToEmpty(String filePath) {
		return ( filePath == null ) ? "" : filePath;
	}

}
