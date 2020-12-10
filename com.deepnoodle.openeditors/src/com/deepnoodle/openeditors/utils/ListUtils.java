package com.deepnoodle.openeditors.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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

	/**
	 * Moves an element in a list to another position.
	 * The other elements are moved accordingly to make space and fill the gap.
	 */
	public static void moveTo(List<?> list, int fromIndex, int toIndex) {
		if( fromIndex < 0 || fromIndex >= list.size() ) {
			throw new IllegalArgumentException( "fromIndex out of range" );
		}
		if( toIndex < 0 || toIndex >= list.size() ) {
			throw new IllegalArgumentException( "toIndex out of range" );
		}

		if( fromIndex < toIndex ) {
			Collections.rotate( list.subList( fromIndex, toIndex + 1 ), -1 );
		} else if( fromIndex > toIndex ) {
			Collections.rotate( list.subList( toIndex, fromIndex + 1 ), 1 );
		}
	}

	@SafeVarargs
	public static <T> List<T> asList(T... xs) {
		return Arrays.asList( xs );
	}

	/**
	 * Creates a shallow copy of the given collection.
	 */
	public static <T> List<T> copy(Collection<T> collection) {
		return new ArrayList<>( collection );
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

	public static <T> Optional<T> findFirst(Collection<T> collection, Predicate<T> predicate) {
		return collection.stream().filter( predicate ).findFirst();
	}

	/**
	 * Creates an Iterable to do a for-each-loop over an array and get at the same time
	 * the current item and the corresponding index.
	 */
	public static <T> Iterable<IndexedEntry<T>> indexed(T[] array) {
		Iterable<IndexedEntry<T>> iterableWithIndex = new Iterable<IndexedEntry<T>>() {
			@Override
			public Iterator<IndexedEntry<T>> iterator() {
				Iterator<IndexedEntry<T>> iteratorWithIndex = new Iterator<IndexedEntry<T>> () {
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

		Iterable<IndexedEntry<T>> iterableWithIndex = new Iterable<IndexedEntry<T>>() {
			@Override
			public Iterator<IndexedEntry<T>> iterator() {
				Iterator<IndexedEntry<T>> iteratorWithIndex = new Iterator<IndexedEntry<T>>() {
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
