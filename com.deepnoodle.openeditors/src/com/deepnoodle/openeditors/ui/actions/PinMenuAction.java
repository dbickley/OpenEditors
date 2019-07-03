package com.deepnoodle.openeditors.ui.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorModel;
import com.deepnoodle.openeditors.ui.EditorTableView;

public class PinMenuAction extends Action {
	private static LogWrapper log = new LogWrapper( PinMenuAction.class );

	private EditorTableView editorTableView;

	public PinMenuAction(EditorTableView editorTableView) {
		this.editorTableView = editorTableView;
		setText( "Pin" );
	}

	@Override
	public void run() {
		List<EditorModel> editors = editorTableView.getSelections();
		for( var editor : editors ) {
			editor.setPinned( true );
		}
		editorTableView.refresh();
	}
}
