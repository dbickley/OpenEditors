package com.deepnoodle.openeditors.models;

import java.util.Comparator;
import java.util.List;

import com.deepnoodle.openeditors.utils.StringUtils;

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
			for( SortType sortType : sortSequence ) {
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
			String name1 = StringUtils.nullToEmpty( editor1.getName() ).toLowerCase();
			String name2 = StringUtils.nullToEmpty( editor2.getName() ).toLowerCase();
			return compare( name1, name2 );
		case PATH :
			String filePath1 = StringUtils.nullToEmpty( editor1.getFilePath() ).toLowerCase();
			String filePath2 = StringUtils.nullToEmpty( editor2.getFilePath() ).toLowerCase();
			return compare( filePath1, filePath2 );
		case EXTENSION :
			String fileExtension1 = StringUtils.nullToEmpty( editor1.getFileExtension() ).toLowerCase();
			String fileExtension2 = StringUtils.nullToEmpty( editor2.getFileExtension() ).toLowerCase();
			return compare( fileExtension1, fileExtension2 );
		default :
			return 0;
		}
	}
}
