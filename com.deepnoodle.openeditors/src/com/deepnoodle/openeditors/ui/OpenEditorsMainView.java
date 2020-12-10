package com.deepnoodle.openeditors.ui;

import static com.deepnoodle.openeditors.utils.ListUtils.copy;

import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import com.deepnoodle.openeditors.Activator;
import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorComparator.SortType;
import com.deepnoodle.openeditors.services.EclipseEditorService;
import com.deepnoodle.openeditors.services.SettingsService;

public class OpenEditorsMainView extends ViewPart {

	private static LogWrapper log = new LogWrapper( OpenEditorsMainView.class );

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
		createToolbar();
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

	private void createToolbar() {

		Action openEditSortSequenceDialog = new Action() {
			@Override
			public void run() {
				Shell shell = getSite().getWorkbenchWindow().getShell();
				List<SortType> sortTypes = settingsService.getSettings().getSortSequence();
				EditSortSequenceDialog dialog = new EditSortSequenceDialog( shell, copy( sortTypes ) );
				int clickedButton = dialog.open();
				if( clickedButton == Window.OK ) {
					List<SortType> newSortSequence = dialog.getSortSequence();
					editorPresenter.setSortSequence( newSortSequence );
				}
			}
		};
		openEditSortSequenceDialog.setText( "Sort..." );
		openEditSortSequenceDialog.setToolTipText( "Edit Sort Sequence..." );
		openEditSortSequenceDialog.setImageDescriptor( Activator.getImageDescriptor( "icons/sort_file.png" ) );

		Action openSwitchToEditorDialog = new Action() {
			@Override
			public void run() {
				IHandlerService handlerService = (IHandlerService) getSite().getService( IHandlerService.class );
				try {
					handlerService.executeCommand( "org.eclipse.ui.window.switchToEditor", null );
				} catch( Exception e ) {
					log.error( e );
				}
			}
		};
		openSwitchToEditorDialog.setText( "Switch to Editor..." );
		openSwitchToEditorDialog.setToolTipText( "Switch to Editor..." );
		openSwitchToEditorDialog.setImageDescriptor( Activator.getImageDescriptor( "icons/switch_to_editor.png" ) );

		// Add the actions to the menu manager.
		IActionBars actionBars = getViewSite().getActionBars();
		IToolBarManager toolBar = actionBars.getToolBarManager();
		toolBar.add( openSwitchToEditorDialog );
		toolBar.add( openEditSortSequenceDialog );
	}

	private void createMenu() {
		//		IActionBars actionBars = getViewSite().getActionBars();
		//		IMenuManager menuManager = actionBars.getMenuManager();
		//      menuManager.add( openEditSortSequenceDialog );
	}
}
