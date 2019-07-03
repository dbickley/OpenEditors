package com.deepnoodle.openeditors.ui.actions;

import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPartSite;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorModel;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.ui.EditorTableView;

public class OpenItemMenuAction extends Action {
	private static LogWrapper log = new LogWrapper( OpenItemMenuAction.class );

	EditorService editorService;

	private EditorTableView editorTableView;

	private IWorkbenchPartSite site;

	public OpenItemMenuAction(EditorTableView editorTableView, IWorkbenchPartSite site, EditorService editorService) {
		this.editorTableView = editorTableView;
		this.site = site;
		this.editorService = editorService;
		setText( "Open" );
	}

	@Override
	public void run() {
		List<EditorModel> editors = editorTableView.getSelections();
		for( var editor : editors ) {
			try {
				editorService.openEditor( editor, site );
			} catch( Exception e ) {
				log.warn( e, "Could not open editor: %s", editor.getFilePath() );
			}
		}
		editorTableView.refresh();
	}
}
