package com.deepnoodle.openeditors.tests;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.eclipse.core.runtime.Path;
import org.junit.jupiter.api.Test;

import com.deepnoodle.openeditors.utils.PathUtils;

public class PathUtilsTest {

	@Test
	public void testFindUniqueSuffix() {
		Path path1 = new Path( "a" );
		Path path2 = new Path( "a/b" );
		Path path3 = new Path( "c/d/a" );
		Path path4 = new Path( "e/d/a" );
		Path path5 = new Path( "c/d" );
		Path path6 = new Path( "e/d" );

		String result1 = PathUtils.findUniqueSuffix( path1, asList( path2 ) );
		assertEquals( "a", result1 );

		String result2 = PathUtils.findUniqueSuffix( path2, asList( path1 ) );
		assertEquals( "b", result2 );

		String result3 = PathUtils.findUniqueSuffix( path3, asList( path1 ) );
		assertEquals( "d/a", result3 );

		String result4 = PathUtils.findUniqueSuffix( path3, asList( path2 ) );
		assertEquals( "a", result4 );

		String result5 = PathUtils.findUniqueSuffix( path4, asList( path3 ) );
		assertEquals( "e/d/a", result5 );

		String result6 = PathUtils.findUniqueSuffix( path3, asList( path4, path5, path6 ) );
		assertEquals( "c/d/a", result6 );
	}

	@Test
	public void testAbbreviateCommonSuffix() {
		Path path1 = new Path( "a" );
		Path path2 = new Path( "a/b" );
		Path path3 = new Path( "c/d/a" );
		Path path4 = new Path( "e/d/a" );
		Path path5 = new Path( "c/d" );
		Path path6 = new Path( "e/d" );
		Path path7 = new Path( "c" );

		String result1 = PathUtils.abbreviateUniqueSuffix( path1, asList( path2 ) );
		assertEquals( "a", result1 );

		String result2 = PathUtils.abbreviateUniqueSuffix( path2, asList( path1 ) );
		assertEquals( "a/b", result2 );

		String result3 = PathUtils.abbreviateUniqueSuffix( path3, asList( path4 ) );
		assertEquals( "c/...", result3 );

		String result4 = PathUtils.abbreviateUniqueSuffix( path3, asList( path4, path5, path6 ) );
		assertEquals( "c/d/a", result4 );

		String result5 = PathUtils.abbreviateUniqueSuffix( path5, asList( path4, path1 ) );
		assertEquals( "c/...", result5 );

		String result6 = PathUtils.abbreviateUniqueSuffix( path5, asList( path4, path7 ) );
		assertEquals( "c/d", result6 );

		Path path100 = new Path( "a/c/d" );
		Path path101 = new Path( "a/c" );
		Path path102 = new Path( "b/c/d" );
		Path path103 = new Path( "b/c" );
		String result100 = PathUtils.abbreviateUniqueSuffix( path100, asList( path101, path102, path103 ) );
		assertEquals( "a/c/d", result100 );
	}
}
