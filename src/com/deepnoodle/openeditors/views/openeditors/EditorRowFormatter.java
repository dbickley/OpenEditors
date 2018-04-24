package com.deepnoodle.openeditors.views.openeditors;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.editor.IEditor;
import com.deepnoodle.openeditors.services.SettingsService;

public class EditorRowFormatter {

	private static LogWrapper log = new LogWrapper(EditorTableView.class);

	private SettingsService settingsService = SettingsService.getInstance();

	private static EditorRowFormatter instance;

	public static EditorRowFormatter getInstance() {
		if (instance == null) {
			instance = new EditorRowFormatter();
		}
		return instance;
	}

	public void formatRows(TableItem[] items, IEditor activeEditor, Color forgroundColor, Color backgroundColor) {

		Color dirtyColor = new Color(Display.getCurrent(), settingsService.getDirtyColor());
		Color pinnedColor = new Color(Display.getCurrent(), settingsService.getPinnedColor());
		Color highlightColor = new Color(Display.getCurrent(), settingsService.getHighlightColor());

		//TODO move to settings
		Color closedColor = new Color(Display.getCurrent(), new RGB(130, 130, 130));

		for (TableItem item : items) {
			try {
				IEditor editor = ((IEditor) item.getData());
				if (editor.isPinned()) {
					item.setForeground(pinnedColor);
					//item.setFont(new F);
				} else if (!editor.isOpened()) {
					item.setForeground(closedColor);
				} else if (editor.isDirty()) {
					item.setForeground(dirtyColor);
				} else {
					item.setForeground(forgroundColor);
				}

				if (activeEditor != null && editor.getFilePath().equals(activeEditor.getFilePath())) {
					item.setBackground(highlightColor);
				} else {
					item.setBackground(backgroundColor);
				}
				item.setChecked(false);
			} catch (Exception e) {
				log.warn(e);
			}
		}
	}
}