package com.deepnoodle.openeditors.views.openeditors;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorPart;

public class PartListener implements IPartListener, IResourceChangeListener {
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
		//Do nothing
	}

	@Override
	public void resourceChanged(IResourceChangeEvent event) {
		//editorTableView.refresh();
	}

}
