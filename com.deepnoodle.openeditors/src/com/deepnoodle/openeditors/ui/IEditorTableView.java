package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.ui.IViewSite;

import com.deepnoodle.openeditors.models.EditorComparator;
import com.deepnoodle.openeditors.models.EditorModel;

public interface IEditorTableView {
	public void setInput(List<EditorModel> editors);

	public IViewSite getViewSite();

	public List<EditorModel> getSelectedEditors();

	public EditorComparator getEditorComparator();
}