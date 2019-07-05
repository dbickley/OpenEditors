package com.deepnoodle.openeditors.ui;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.deepnoodle.openeditors.ui.EditorTableView.IEditorTableViewPresenter;

public class EditorContentProvider implements IStructuredContentProvider {

	IEditorTableViewPresenter presenter;

	EditorContentProvider(IEditorTableViewPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return presenter.getSortedEditors().toArray();
	}
}
