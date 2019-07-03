package com.deepnoodle.openeditors.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class ListUtils {

	private ListUtils() {
	}

	public static <T> boolean isNullOrEmpty(Collection<T> collection) {
		return ( collection == null || collection.isEmpty() );
	}

	public static <T> List<T> toList(Collection<T> collection) {
		if( collection instanceof List ) {
			return (List<T>) collection;
		} else {
			return collection.stream().collect( Collectors.toList() );
		}
	}

	public static <T> List<T> toList(T[] array) {
		List<T> result = new ArrayList<>( array.length );
		for( T element : array ) {
			result.add( element );
		}
		return result;
	}

	public static <T, R> List<R> map(T[] collection, Function<T, R> mapper) {
		List<R> result = Arrays.stream( collection ).map( mapper ).collect( Collectors.toList() );
		return result;
	}

	public static <T, R> List<R> map(Collection<T> collection, Function<T, R> mapper) {
		List<R> result = collection.stream().map( mapper ).collect( Collectors.toList() );
		return result;
	}

	public static <T> List<T> filter(Collection<T> collection, Predicate<T> predicate) {
		List<T> result = collection.stream().filter( predicate ).collect( Collectors.toList() );
		return result;
	}

	/**
	 * Creates an Iterable to do a for-each-loop over an array and get at the same time
	 * the current item and the corresponding index.
	 */
	public static <T> Iterable<IndexedEntry<T>> indexed(T[] array) {
		Iterable<IndexedEntry<T>> iterableWithIndex = new Iterable<>() {
			@Override
			public Iterator<IndexedEntry<T>> iterator() {
				Iterator<IndexedEntry<T>> iteratorWithIndex = new Iterator<>() {
					Integer currentIndex = 0;

					@Override
					public boolean hasNext() {
						return currentIndex < array.length;
					}

					@Override
					public IndexedEntry<T> next() {
						T currentObject = array[currentIndex];
						IndexedEntry<T> result = new IndexedEntry<>( currentObject, currentIndex );
						currentIndex++;
						return result;
					}

				};

				return iteratorWithIndex;
			}
		};

		return iterableWithIndex;
	}

	/**
	 * Creates an Iterable to do a for-each-loop over a normal iterable and get at the same time
	 * the current item and the corresponding index.
	 */
	public static <T> Iterable<IndexedEntry<T>> indexed(Iterable<T> iterable) {
		Iterator<T> iterator = iterable.iterator();

		Iterable<IndexedEntry<T>> iterableWithIndex = new Iterable<>() {
			@Override
			public Iterator<IndexedEntry<T>> iterator() {
				Iterator<IndexedEntry<T>> iteratorWithIndex = new Iterator<>() {
					Integer currentIndex = 0;

					@Override
					public boolean hasNext() {
						return iterator.hasNext();
					}

					@Override
					public IndexedEntry<T> next() {
						T currentObject = iterator.next();
						IndexedEntry<T> result = new IndexedEntry<>( currentObject, currentIndex );
						currentIndex++;
						return result;
					}

				};

				return iteratorWithIndex;
			}
		};

		return iterableWithIndex;
	}

}
