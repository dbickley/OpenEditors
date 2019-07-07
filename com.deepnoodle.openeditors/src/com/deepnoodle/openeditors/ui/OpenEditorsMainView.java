package com.deepnoodle.openeditors.ui;

import static com.deepnoodle.openeditors.utils.ListUtils.copy;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.deepnoodle.openeditors.models.EditorComparator.SortType;
import com.deepnoodle.openeditors.services.EclipseEditorService;
import com.deepnoodle.openeditors.services.SettingsService;

public class OpenEditorsMainView extends ViewPart {

	private EditorPresenter editorPresenter;
	private EclipseEditorService editorService;
	private SettingsService settingsService;
	private EditorTableView editorTableView;

	@Override
	@PostConstruct
	public void createPartControl(Composite parent) {
		editorService = new EclipseEditorService();
		settingsService = new SettingsService();
		editorPresenter = new EditorPresenter( editorService, settingsService );
		editorTableView = new EditorTableView( parent, getViewSite(), editorPresenter );

		// Listen to opened and closed editors
		getSite().getWorkbenchWindow().getPartService().addPartListener( editorPresenter );

		createMenu();
	}

	@Override
	public void setFocus() {
		// Do nothing
	}

	@Override
	public void dispose() {
		// Remove all listeners
		getSite().getWorkbenchWindow().getPartService().removePartListener( editorPresenter );
		editorPresenter.dispose();
	}

	private void createMenu() {
		Action openEditSortSequenceDialog = new Action() {
			@Override
			public void run() {
				var shell = getSite().getWorkbenchWindow().getShell();
				List<SortType> sortTypes = settingsService.getSettings().getSortSequence();
				var dialog = new EditSortSequenceDialog( shell, copy( sortTypes ) );
				var clickedButton = dialog.open();
				if( clickedButton == Window.OK ) {
					System.out.println( "ok clicked" );
					var newSortSequence = dialog.getSortSequence();
					editorPresenter.setSortSequence( newSortSequence );
				}
			}
		};
		openEditSortSequenceDialog.setText( "Edit Sort Sequence..." );

		// Add the actions to the menu manager.
		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();
		menuManager.add( openEditSortSequenceDialog );
	}
}
