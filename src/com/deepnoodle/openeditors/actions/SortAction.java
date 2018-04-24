package com.deepnoodle.openeditors.actions;

import org.eclipse.jface.action.Action;

import com.deepnoodle.openeditors.models.editor.EditorComparator.SortType;
import com.deepnoodle.openeditors.views.openeditors.EditorTableView;

public class SortAction extends Action {
	protected EditorTableView editorTableView;
	protected SortType sortType;

	public SortAction(EditorTableView editorTableView, SortType sortType, String text, String tooltip) {
		this.editorTableView = editorTableView;
		this.sortType = sortType;
		setText(text);
		setToolTipText(tooltip);
	}

	@Override
	public void run() {
		editorTableView.getSorter().setSortBy(sortType);
		editorTableView.refresh();
	}

}
