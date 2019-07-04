package com.deepnoodle.openeditors.ui.actions;

import org.eclipse.jface.action.Action;

import com.deepnoodle.openeditors.models.EditorComparator.SortType;

public class SortAction extends Action {
	protected ISortActionCallback callback;
	protected SortType sortType;

	public interface ISortActionCallback {
		void setSortBy(SortType sortType);
	}

	public SortAction(ISortActionCallback callback, SortType sortType, String text, String tooltip) {
		this.callback = callback;
		this.sortType = sortType;
		setText( text );
		setToolTipText( tooltip );
	}

	@Override
	public void run() {
		callback.setSortBy( sortType );
	}

}
