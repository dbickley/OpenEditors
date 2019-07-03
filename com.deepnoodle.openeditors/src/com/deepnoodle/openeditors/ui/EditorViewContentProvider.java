package com.deepnoodle.openeditors.ui;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.deepnoodle.openeditors.services.EditorService;

class EditorViewContentProvider implements IStructuredContentProvider {

	private EditorService editorService;

	public EditorViewContentProvider(EditorService editorService) {
		this.editorService = editorService;
	}

	@Override
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		//Do nothing
	}

	@Override
	public void dispose() {
		//Do nothing
	}

	@Override
	public Object[] getElements(Object parent) {
		return editorService.buildOpenEditors().toArray();
	}
}