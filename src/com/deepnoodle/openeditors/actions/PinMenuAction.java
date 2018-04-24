package com.deepnoodle.openeditors.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.editor.IEditor;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.services.SettingsService;
import com.deepnoodle.openeditors.views.openeditors.EditorTableView;

public class PinMenuAction extends Action {
	private static LogWrapper log = new LogWrapper(PinMenuAction.class);

	EditorService editorService = EditorService.getInstance();
	SettingsService settingsService = SettingsService.getInstance();
	private EditorTableView editorTableView;

	public PinMenuAction(EditorTableView editorTableView) {
		this.editorTableView = editorTableView;
		setText("Pin");
	}

	@Override
	public void run() {
		List<IEditor> editors = editorTableView.getSelections();
		for (IEditor editor : editors) {
			try {
				editor.setPinned(true);
			} catch (Exception e) {
				log.warn(e, "Could not close editor: %s", editor.getFilePath());
			}
		}
		settingsService.saveSettings();
		editorTableView.refresh();
	}
}
