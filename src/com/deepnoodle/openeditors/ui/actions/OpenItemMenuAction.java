package com.deepnoodle.openeditors.ui.actions;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPartSite;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.IEditor;
import com.deepnoodle.openeditors.persistence.SettingsService;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.ui.EditorTableView;

public class OpenItemMenuAction extends Action {
	private static LogWrapper log = new LogWrapper(OpenItemMenuAction.class);

	EditorService editorService = EditorService.getInstance();
	SettingsService settingsService = SettingsService.getInstance();
	private EditorTableView editorTableView;

	private IWorkbenchPartSite site;

	public OpenItemMenuAction(EditorTableView editorTableView, IWorkbenchPartSite site) {
		this.editorTableView = editorTableView;
		this.site = site;
		setText("Open");
	}

	@Override
	public void run() {
		List<IEditor> editors = editorTableView.getSelections();
		for (IEditor editor : editors) {
			try {
				if (!editor.isOpened()) {
					editorService.openEditor(editor, site);
				}
			} catch (Exception e) {
				log.warn(e, "Could not open editor: %s", editor.getFilePath());
			}
		}
		editorTableView.refresh();
	}
}
