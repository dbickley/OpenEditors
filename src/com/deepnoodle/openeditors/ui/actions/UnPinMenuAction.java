package com.deepnoodle.openeditors.ui.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.IEditor;
import com.deepnoodle.openeditors.persistence.SettingsService;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.ui.EditorTableView;

public class UnPinMenuAction extends Action {
	private static LogWrapper log = new LogWrapper(PinMenuAction.class);

	EditorService editorService = EditorService.getInstance();
	SettingsService settingsService = SettingsService.getInstance();
	private EditorTableView editorTableView;

	public UnPinMenuAction(EditorTableView editorTableView) {
		this.editorTableView = editorTableView;
		setText("Un-Pin");
	}

	@Override
	public void run() {
		List<IEditor> editors = editorTableView.getSelections();
		for (IEditor editor : editors) {
			try {
				editor.setPinned(false);
			} catch (Exception e) {
				log.warn(e, "Could not close editor: %s", editor.getFilePath());
			}
		}
		settingsService.saveSettings();
		editorTableView.refresh();
	}
}
