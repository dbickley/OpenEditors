package com.deepnoodle.openeditors.tests;

import static com.deepnoodle.openeditors.utils.ListUtils.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ListUtilsTest {

	@Test
	public void testIndexed() {
		var list1 = asList( "a", "b", "c", "d", "e", "f" );
		for( var entryWithIndex : indexed( list1 ) ) {
			assertTrue( entryWithIndex.getIndex() == list1.indexOf( entryWithIndex.getEntry() ) );
		}
	}

	@Test
	public void testMoveTo() {
		var list1 = asList( "a", "b", "c", "d", "e" );
		moveTo( list1, list1.indexOf( "b" ), list1.indexOf( "d" ) );
		assertEquals( asList( "a", "c", "d", "b", "e" ), list1 );

		moveTo( list1, list1.indexOf( "b" ), list1.indexOf( "c" ) );
		assertEquals( asList( "a", "b", "c", "d", "e" ), list1 );
	}

}
