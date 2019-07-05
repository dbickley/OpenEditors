package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.ui.IViewSite;

import com.deepnoodle.openeditors.models.EditorModel;

public interface IEditorTableView {
	public void refresh();

	public IViewSite getViewSite();

	public List<EditorModel> getSelectedEditors();
}