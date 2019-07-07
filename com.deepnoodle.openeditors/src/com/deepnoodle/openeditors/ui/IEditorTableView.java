package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.ui.IViewSite;

import com.deepnoodle.openeditors.models.EditorModel;

public interface IEditorTableView {
	void setInput(List<EditorModel> editors);

	IViewSite getViewSite();

	List<EditorModel> getSelectedEditors();
}