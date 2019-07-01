package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPartSite;

import com.deepnoodle.openeditors.models.IEditor;
import com.deepnoodle.openeditors.ui.actions.CloseItemMenuAction;
import com.deepnoodle.openeditors.ui.actions.OpenItemMenuAction;
import com.deepnoodle.openeditors.ui.actions.PinMenuAction;
import com.deepnoodle.openeditors.ui.actions.UnPinMenuAction;

public class EditorItemMenuManager implements IMenuListener {
	//private static LogWrapper log = new LogWrapper(EditorItemMenuManager.class);

	private EditorTableView editorTableView;

	private ActionContributionItem pinMenuItem;

	private ActionContributionItem unPinMenuItem;

	private ActionContributionItem openMenuItem;

	private ActionContributionItem closeMenuItem;

	private MenuManager menuManager;

	//TODO if performance issues, find a better way then rebuilding the menu everytime
	public EditorItemMenuManager(final EditorTableView editorTableView, final IWorkbenchPartSite site,
			Composite parent) {
		this.editorTableView = editorTableView;
		menuManager = new MenuManager() {

			@Override
			public void fill(Menu parent, int index) {
				super.fill(parent, index);
			}

		};
		menuManager.addMenuListener(this);
		menuManager.setRemoveAllWhenShown(true);

		pinMenuItem = new ActionContributionItem(new PinMenuAction(editorTableView));
		menuManager.add(pinMenuItem);

		unPinMenuItem = new ActionContributionItem(new UnPinMenuAction(editorTableView));
		menuManager.add(unPinMenuItem);

		openMenuItem = new ActionContributionItem(new OpenItemMenuAction(editorTableView, site));
		menuManager.add(openMenuItem);

		closeMenuItem = new ActionContributionItem(new CloseItemMenuAction(editorTableView, site));
		menuManager.add(closeMenuItem);

	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {

		List<IEditor> selections = editorTableView.getSelections();
		//TODO should I just add the ones I want or set visibility and add all?

		pinMenuItem.setVisible(canPin(selections));
		unPinMenuItem.setVisible(canUnPin(selections));
		openMenuItem.setVisible(canOpen(selections));
		closeMenuItem.setVisible(canClose(selections));

		menuManager.add(pinMenuItem);
		menuManager.add(unPinMenuItem);
		menuManager.add(openMenuItem);
		menuManager.add(closeMenuItem);

	}

	private boolean canPin(List<IEditor> editors) {
		for (IEditor editor : editors) {
			if (!editor.isPinned()) {
				return true;
			}
		}
		return false;
	}

	private boolean canUnPin(List<IEditor> editors) {
		for (IEditor editor : editors) {
			if (editor.isPinned()) {
				return true;
			}
		}
		return false;
	}

	private boolean canClose(List<IEditor> editors) {
		return true;
	}

	private boolean canOpen(List<IEditor> editors) {
		return false;
	}

	public Menu createContextMenu(Control parent) {
		return menuManager.createContextMenu(parent);
	}

}
