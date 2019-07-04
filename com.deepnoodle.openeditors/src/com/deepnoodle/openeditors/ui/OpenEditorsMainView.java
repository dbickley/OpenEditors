package com.deepnoodle.openeditors.ui;

import javax.annotation.PostConstruct;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.deepnoodle.openeditors.models.EditorComparator;
import com.deepnoodle.openeditors.services.EclipseEditorService;
import com.deepnoodle.openeditors.services.SettingsService;
import com.deepnoodle.openeditors.ui.actions.SortAction;

public class OpenEditorsMainView extends ViewPart {

	private EditorPresenter editorPresenter;

	@Override
	@PostConstruct
	public void createPartControl(Composite parent) {
		EclipseEditorService editorService = new EclipseEditorService();
		SettingsService settingsService = new SettingsService();
		editorPresenter = new EditorPresenter( editorService, settingsService );
		EditorTableView editorTableView = new EditorTableView( parent, getViewSite(), editorPresenter );

		// Listen to opened and closed editors
		getSite().getWorkbenchWindow().getPartService().addPartListener( editorPresenter );

		// @formatter:off
		Action sortByNameAction = new SortAction(editorPresenter,
			EditorComparator.SortType.NAME,
			"Sort by Name",
			"Sorts the tabs by name");
		Action sortByPathAction = new SortAction(editorPresenter,
			EditorComparator.SortType.PATH,
			"Sort by Path",
			"Sorts the tabs by full path");
		// @formatter:on

		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();
		menuManager.add( sortByNameAction );
		menuManager.add( sortByPathAction );
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
}
