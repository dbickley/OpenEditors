package com.deepnoodle.openeditors.models;

import java.util.Comparator;

public class EditorComparator implements Comparator<EditorModel> {
	public enum SortType {
		ACCESS, NATURAL, NAME, PATH
	}

	private SortType sortBy;

	public EditorComparator(SortType sortBy) {
		this.sortBy = sortBy;
	}

	@Override
	public int compare(EditorModel editor1, EditorModel editor2) {
		int compare = Boolean.compare( editor2.isPinned(), editor1.isPinned() );
		if( compare == 0 ) {
			switch( sortBy ) {
			case ACCESS :
				compare = compare( editor2.getLastAccessTime(), editor1.getLastAccessTime() );
				break;
			case NATURAL :
				compare = compare( editor1.getNaturalPosition(), editor2.getNaturalPosition() );
				break;
			case NAME :
				compare = compare( editor1.getName().toLowerCase(), editor2.getName().toLowerCase() );
				break;
			case PATH :
				compare = compare( editor1.getFilePath().toLowerCase(), editor2.getFilePath().toLowerCase() );
				break;
			default :
				break;
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

	public SortType getSortBy() {
		return sortBy;
	}

	public void setSortBy(SortType sortBy) {
		this.sortBy = sortBy;
	}
}
