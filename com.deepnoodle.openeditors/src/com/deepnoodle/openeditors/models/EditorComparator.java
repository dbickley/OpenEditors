package com.deepnoodle.openeditors.models;

import java.util.Comparator;
import java.util.List;

public class EditorComparator implements Comparator<EditorModel> {
	public enum SortType {
		ACCESS, NATURAL, NAME, PATH, EXTENSION
	}

	private List<SortType> sortSequence;

	public EditorComparator(List<SortType> sortSequence) {
		this.setSortSequence( sortSequence );
	}

	@Override
	public int compare(EditorModel editor1, EditorModel editor2) {
		int compare = Boolean.compare( editor2.isPinned(), editor1.isPinned() );
		if( compare == 0 ) {
			for( var sortType : sortSequence ) {
				compare = compare( editor1, editor2, sortType );
				if( compare != 0 ) {
					return compare;
				}
			}
		}
		return compare;
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	public int compare(final Comparable one, final Comparable two) {
		if( one == null ^ two == null ) {
			return ( one == null ) ? -1 : 1;
		}

		if( one == null && two == null ) {
			return 0;
		}

		return one.compareTo( two );
	}

	public List<SortType> getSortSequence() {
		return sortSequence;
	}

	public void setSortSequence(List<SortType> sortSequence) {
		this.sortSequence = sortSequence;
	}

	private int compare(EditorModel editor1, EditorModel editor2, SortType sortType) {
		switch( sortType ) {
		case ACCESS :
			return compare( editor2.getLastAccessTime(), editor1.getLastAccessTime() );
		case NATURAL :
			return compare( editor1.getNaturalPosition(), editor2.getNaturalPosition() );
		case NAME :
			return compare( editor1.getName().toLowerCase(), editor2.getName().toLowerCase() );
		case PATH :
			return compare( editor1.getFilePath().toLowerCase(), editor2.getFilePath().toLowerCase() );
		case EXTENSION :
			return compare( editor1.getFileExtension().toLowerCase(), editor2.getFileExtension().toLowerCase() );
		default :
			return 0;
		}
	}
}
