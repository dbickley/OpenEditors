package com.deepnoodle.openeditors.ui;

import javax.annotation.PostConstruct;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.deepnoodle.openeditors.models.EditorComparator;
import com.deepnoodle.openeditors.models.EditorComparator.SortType;
import com.deepnoodle.openeditors.services.EclipseEditorService;
import com.deepnoodle.openeditors.services.SettingsService;

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

		Action sortByNameAction =
		    createSortAction( EditorComparator.SortType.NAME, "Sort by Name", "Sorts the editors by name" );
		Action sortByPathAction =
		    createSortAction( EditorComparator.SortType.PATH, "Sort by Path", "Sorts the editors by full path" );
		Action sortByAccessAction = createSortAction( EditorComparator.SortType.ACCESS, "Sort by Access",
		    "Sorts the editors by the last access time" );
		Action sortByNaturalAction = createSortAction( EditorComparator.SortType.NATURAL, "Sort by Tab Order",
		    "Sorts the editors in the order of the corresponding tabs" );

		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();
		menuManager.add( sortByNameAction );
		menuManager.add( sortByPathAction );
		menuManager.add( sortByAccessAction );
		menuManager.add( sortByNaturalAction );
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

	public interface ISortActionCallback {
		void setSortBy(SortType sortType);
	}

	private Action createSortAction(SortType sortType, String title, String tooltip) {
		Action sortAction = new Action() {
			@Override
			public void run() {
				editorPresenter.setSortBy( sortType );
			}
		};
		sortAction.setText( title );
		sortAction.setToolTipText( tooltip );
		return sortAction;
	}
}
