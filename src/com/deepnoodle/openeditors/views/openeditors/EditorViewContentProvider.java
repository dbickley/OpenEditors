package com.deepnoodle.openeditors.views.openeditors;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.deepnoodle.openeditors.services.EditorService;

class EditorViewContentProvider implements IStructuredContentProvider {

	private EditorService openEditorService = EditorService.getInstance();

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
		return openEditorService.buildOpenEditors().toArray();
	}
}