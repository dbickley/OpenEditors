package com.deepnoodle.openeditors.tests;

import static com.deepnoodle.openeditors.utils.ListUtils.indexed;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

public class ListUtilsTest {

	@Test
	public void testIndexed() {
		var list1 = asList( "a", "b", "c", "d", "e", "f" );
		for( var entryWithIndex : indexed( list1 ) ) {
			assertTrue( entryWithIndex.getIndex() == list1.indexOf( entryWithIndex.getEntry() ) );
		}
	}

}
