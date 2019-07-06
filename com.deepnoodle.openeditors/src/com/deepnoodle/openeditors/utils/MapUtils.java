package com.deepnoodle.openeditors.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class MapUtils {

	private MapUtils() {
	}

	/**
	 * Given a map from keys to lists of elements, this method adds a new value to the list for the
	 * given key. If there is no list for the given key yet, then a new list with the element
	 * will be created and put in the map. 
	 */
	public static <K, V> void putAppending(Map<K, List<V>> map, K key, V newValue) {
		List<V> values = map.getOrDefault( key, new ArrayList<V>() );
		values.add( newValue );
		map.put( key, values );
	}

}