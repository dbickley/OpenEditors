package com.deepnoodle.openeditors.ui.actions;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPartSite;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.IEditor;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.ui.EditorTableView;

public class CloseItemMenuAction extends Action {
	private static LogWrapper log = new LogWrapper( CloseItemMenuAction.class );

	EditorService editorService;
	private EditorTableView editorTableView;

	private IWorkbenchPartSite site;

	public CloseItemMenuAction(EditorTableView editorTableView, IWorkbenchPartSite site, EditorService editorService) {
		this.editorTableView = editorTableView;
		this.site = site;
		this.editorService = editorService;
		setText( "Close" );
	}

	@Override
	public void run() {
		List<IEditor> editors = editorTableView.getSelections();
		for( IEditor editor : editors ) {
			try {
				editorService.closeEditor( editor, site );
			} catch( Exception e ) {
				log.warn( e, "Could not close editor: %s", editor.getFilePath() );
			}
		}
		editorTableView.refresh();
	}
}
