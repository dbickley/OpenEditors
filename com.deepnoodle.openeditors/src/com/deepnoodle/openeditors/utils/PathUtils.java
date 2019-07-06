package com.deepnoodle.openeditors.utils;

import java.util.List;
import java.util.Objects;

import org.eclipse.core.runtime.IPath;

public final class PathUtils {
	// TODO: These methods have poor performance because they repeatedly look for the same suffixes in paths.
	// This could be improved with a cache from (path, suffixLength) to (suffix)

	// TODO: I think prefix and suffix operations could be abstracted to use the same methods.

	private PathUtils() {
	}

	/**
	 * Returns the shortest prefix in path that is no prefix in any of the other paths.
	 * If no such prefix exists, then the full path is returned.
	 */
	public static String findUniquePrefix(IPath path, List<IPath> otherPaths) {
		int segmentCount = path.segmentCount();
		String prefix = "";
		for( int prefixLength = 1; prefixLength <= segmentCount; prefixLength++ ) {
			prefix = findPrefix( path, segmentCount, prefixLength );
			boolean isUnique = isPrefixUnique( prefix, prefixLength, otherPaths );
			if( isUnique ) {
				break;
			}
		}
		return prefix;
	}

	/**
	 * Returns the prefix of given length in path.
	 * Thereby, segmentCount must be the number of segments in path.
	 * If prefixLength is bigger than segmentCount then the full path is returned. 
	 * @param path
	 * @param segmentCount
	 * @param prefixLength
	 * @return
	 */
	public static String findPrefix(IPath path, int segmentCount, int prefixLength) {
		String result = path.segment( 0 );
		int prefixLengthInRange = ( prefixLength <= segmentCount ) ? prefixLength : segmentCount;
		for( int i = 1; i <= prefixLengthInRange - 1; i++ ) {
			result = result + "/" + path.segment( i );
		}
		return result;
	}

	/**
	 * Returns true if and only if the given prefix is different
	 * from all of the prefixed of the given length in the other paths.
	 * If it is equal to any of these, false is returned.
	 */
	public static boolean isPrefixUnique(String prefix, int prefixLength, List<IPath> otherPaths) {
		for( IPath otherPath : otherPaths ) {
			String otherPrefix = findPrefix( otherPath, otherPath.segmentCount(), prefixLength );
			if( Objects.equals( prefix, otherPrefix ) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Returns the shortest suffix in path that is no suffix in any of the other paths.
	 * If no such suffix exists, then the full path is returned.
	 */
	public static String findUniqueSuffix(IPath path, List<IPath> otherPaths) {
		int segmentCount = path.segmentCount();
		String suffix = "";
		for( int suffixLength = 1; suffixLength <= segmentCount; suffixLength++ ) {
			suffix = findSuffix( path, segmentCount, suffixLength );
			boolean isUnique = isSuffixUnique( suffix, suffixLength, otherPaths );
			if( isUnique ) {
				break;
			}
		}
		return suffix;
	}

	/**
	 * Returns the suffix of given length in path.
	 * Thereby, segmentCount must be the number of segments in path.
	 * If suffixLength is bigger than segmentCount then the full path is returned. 
	 * @param path
	 * @param segmentCount
	 * @param suffixLength
	 * @return
	 */
	public static String findSuffix(IPath path, int segmentCount, int suffixLength) {
		String result = path.segment( segmentCount - 1 );
		int suffixLengthInRange = ( suffixLength <= segmentCount ) ? suffixLength : segmentCount;
		for( int i = 2; i <= suffixLengthInRange; i++ ) {
			result = path.segment( segmentCount - i ) + "/" + result;
		}
		return result;
	}

	/**
	 * Returns true if and only if the given suffix is different
	 * from all of the suffixes of the given length in the other paths.
	 * If it is equal to any of these, false is returned.
	 */
	public static boolean isSuffixUnique(String suffix, int suffixLength, List<IPath> otherPaths) {
		for( IPath otherPath : otherPaths ) {
			String otherSuffix = findSuffix( otherPath, otherPath.segmentCount(), suffixLength );
			if( Objects.equals( suffix, otherSuffix ) ) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Abbreviates the given unique suffix of a path such that it still unique.
	 */
	public static String abbreviateUniqueSuffix(IPath path, List<IPath> otherPaths) {
		String uniquePrefix = findUniquePrefix( path, otherPaths );
		if( uniquePrefix.length() < path.toString().length() ) {
			return uniquePrefix + "/" + "...";
		} else {
			return path.toString();
		}
	}

	/**
	 * Returns the given path minus its last element.
	 */
	public static IPath getParentPath(IPath path) {
		return path.uptoSegment( path.segmentCount() - 1 );
	}
}
