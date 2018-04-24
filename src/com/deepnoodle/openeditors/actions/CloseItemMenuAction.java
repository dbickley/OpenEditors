package com.deepnoodle.openeditors.actions;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPartSite;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.editor.IEditor;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.services.SettingsService;
import com.deepnoodle.openeditors.views.openeditors.EditorTableView;

public class CloseItemMenuAction extends Action {
	private static LogWrapper log = new LogWrapper(CloseItemMenuAction.class);

	EditorService editorService = EditorService.getInstance();
	SettingsService settingsService = SettingsService.getInstance();
	private EditorTableView editorTableView;

	private IWorkbenchPartSite site;

	public CloseItemMenuAction(EditorTableView editorTableView, IWorkbenchPartSite site) {
		this.editorTableView = editorTableView;
		setText("Close and Remove");
	}

	@Override
	public void run() {
		List<IEditor> editors = editorTableView.getSelections();
		for (IEditor editor : editors) {
			try {
				if (editor.isOpened()) {
					editorService.closeEditor(editor, site);
				}
				settingsService.getActiveEditorSettingsSet().getEditorModels().remove(editor.getFilePath());
			} catch (Exception e) {
				log.warn(e, "Could not close editor: %s", editor.getFilePath());
			}
		}

		settingsService.saveSettings();
		editorTableView.refresh();
	}
}
