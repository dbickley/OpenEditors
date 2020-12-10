package com.deepnoodle.openeditors.tests;

import static com.deepnoodle.openeditors.utils.ListUtils.*;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.deepnoodle.openeditors.utils.IndexedEntry;

public class ListUtilsTest {

	@Test
	public void testIndexed() {
		List<String> list1 = asList( "a", "b", "c", "d", "e", "f" );
		for( IndexedEntry<String> entryWithIndex : indexed( list1 ) ) {
			assertTrue( entryWithIndex.getIndex() == list1.indexOf( entryWithIndex.getEntry() ) );
		}
	}

	@Test
	public void testMoveTo() {
		List<String> list1 = asList( "a", "b", "c", "d", "e" );
		moveTo( list1, list1.indexOf( "b" ), list1.indexOf( "d" ) );
		assertEquals( asList( "a", "c", "d", "b", "e" ), list1 );

		moveTo( list1, list1.indexOf( "b" ), list1.indexOf( "c" ) );
		assertEquals( asList( "a", "b", "c", "d", "e" ), list1 );
	}

}
