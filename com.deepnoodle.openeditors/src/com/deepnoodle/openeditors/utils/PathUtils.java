package com.deepnoodle.openeditors.utils;

import java.nio.file.Paths;

import org.eclipse.core.runtime.IPath;

public final class PathUtils {

	private PathUtils() {
	}
	
	public static java.nio.file.Path toJavaPath(IPath path) {
		return Paths.get(path.toOSString());
	}
	
}
