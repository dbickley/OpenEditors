package com.deepnoodle.openeditors.views.openeditors;

import javax.annotation.PostConstruct;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.part.ViewPart;

import com.deepnoodle.openeditors.actions.SortAction;
import com.deepnoodle.openeditors.models.editor.EditorComparator;

//TODO clean this up
public class OpenEditorsMainView extends ViewPart {

	private EditorTableView editorTableView;

	private PartListener partListener;
	
	@Override
	@PostConstruct
	public void createPartControl(Composite parent) {

		//Build the editor view
		editorTableView = new EditorTableView(parent, getSite(), getViewSite());

		partListener = new PartListener(editorTableView);
		getSite().getWorkbenchWindow().getPartService().addPartListener(partListener);
		
		//EditorSetComboControl editorSetComboControl = new EditorSetComboControl(editorTableView);

//		Action loadSetAction = new ManageSetsAction(editorSetComboControl);
//		Action saveSetAction = new SaveSetAction(editorSetComboControl);
//
//		Action sortByAccessAction = new SortAction(editorTableView,
//				EditorComparator.SortType.ACCESS,
//				"Sort by Last Access",
//				"Sorts the tabs by last access using eclipse navigation history");
		Action sortByNameAction = new SortAction(editorTableView,
				EditorComparator.SortType.NAME,
				"Sort by Name",
				"Sorts the tabs by name");
		Action sortByPathAction = new SortAction(editorTableView,
				EditorComparator.SortType.PATH,
				"Sort by Path",
				"Sorts the tabs by full path");

		IActionBars bars = getViewSite().getActionBars();
		IMenuManager menuManager = bars.getMenuManager();
		menuManager.add(sortByNameAction);
		menuManager.add(sortByPathAction);

		//TODO fix and add back in
		//menuManager.add(sortByAccessAction);

		//TODO fix and add back in
		//		IToolBarManager toolbarManager = bars.getToolBarManager();
		//
		//		toolbarManager.add(editorSetComboControl);
		//		toolbarManager.add(loadSetAction);
		//		toolbarManager.add(saveSetAction);

	}

	@Override
	public void setFocus() {
		// Do nothing
	}

	@Override
	public void dispose() {
		// Remove all listeners
		getSite().getWorkbenchWindow().getPartService().removePartListener(partListener);
		editorTableView.dispose();
	}
}
