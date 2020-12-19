package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;

import com.deepnoodle.openeditors.models.EditorModel;
import com.deepnoodle.openeditors.ui.EditorTableView.IEditorTableViewPresenter;

public class EditorContentProvider implements IStructuredContentProvider {

	IEditorTableViewPresenter presenter;

	EditorContentProvider(IEditorTableViewPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Object[] getElements(Object inputElement) {
	    List<EditorModel> editors = presenter.getSortedEditors();
		return editors.toArray();
	}
}
