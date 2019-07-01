package com.deepnoodle.openeditors.ui;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorPart;

public class PartListener implements IPartListener {
	private EditorTableView editorTableView;

	public PartListener(EditorTableView editorTableView) {
		this.editorTableView = editorTableView;
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			editorTableView.setActivePart(part);
			editorTableView.refresh();
		}
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			editorTableView.refresh();
		}
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			editorTableView.refresh();
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		if (part instanceof EditorPart) {
			editorTableView.refresh();
		}

	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		// Do nothing
		editorTableView.setActivePart(null);
	}
}
