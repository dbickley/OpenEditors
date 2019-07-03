package com.deepnoodle.openeditors.ui;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.TableItem;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.IEditor;
import com.deepnoodle.openeditors.services.SettingsService;

public class EditorRowFormatter {

	private static LogWrapper log = new LogWrapper( EditorTableView.class );

	private Color dirtyColor;
	private Color pinnedColor;
	private Color highlightColor;

	public EditorRowFormatter(SettingsService settingsService) {
		dirtyColor = new Color( Display.getCurrent(), settingsService.getSettings().getDirtyColor() );
		pinnedColor = new Color( Display.getCurrent(), settingsService.getSettings().getPinnedColor() );
		highlightColor = new Color( Display.getCurrent(), settingsService.getSettings().getHighlightColor() );
	}

	public void formatRows(TableItem[] items, IEditor activeEditor, Color forgroundColor, Color backgroundColor) {
		for( TableItem item : items ) {
			try {
				IEditor editor = ( (IEditor) item.getData() );
				if( editor.isPinned() ) {
					item.setForeground( pinnedColor );
				} else if( editor.isDirty() ) {
					item.setForeground( dirtyColor );
				} else {
					item.setForeground( forgroundColor );
				}

				if( activeEditor != null && editor.getFilePath().equals( activeEditor.getFilePath() ) ) {
					item.setBackground( highlightColor );
				} else {
					item.setBackground( backgroundColor );
				}
				item.setChecked( false );
			} catch( Exception e ) {
				log.warn( e );
			}
		}
	}
}