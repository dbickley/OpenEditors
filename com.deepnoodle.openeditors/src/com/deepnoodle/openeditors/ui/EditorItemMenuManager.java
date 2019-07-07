package com.deepnoodle.openeditors.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchPartSite;

public class EditorItemMenuManager implements IMenuListener {

	private IEditorItemMenuManagerCallback callback;

	private MenuManager menuManager;

	private ActionContributionItem pinMenuItem;
	private ActionContributionItem unPinMenuItem;
	private ActionContributionItem closeMenuItem;

	public interface IEditorItemMenuManagerCallback {
		void onCloseSelectedEditors();

		void onPinSelectedEditors();

		void onUnPinSelectedEditors();

		boolean canPinSelectedEditors();

		boolean canUnPinSelectedEditors();
	}
	
	//TODO if performance issues, find a better way then rebuilding the menu everytime
	public EditorItemMenuManager(IEditorItemMenuManagerCallback callback, final IWorkbenchPartSite site,
	    Composite parent) {

		this.callback = callback;

		menuManager = new MenuManager();
		menuManager.addMenuListener( this );
		menuManager.setRemoveAllWhenShown( true );

		pinMenuItem = new ActionContributionItem( new EditorItemMenuAction( "Pin", callback::onPinSelectedEditors ) );
		menuManager.add( pinMenuItem );

		unPinMenuItem =
		    new ActionContributionItem( new EditorItemMenuAction( "Un-Pin", callback::onUnPinSelectedEditors ) );
		menuManager.add( unPinMenuItem );

		closeMenuItem =
		    new ActionContributionItem( new EditorItemMenuAction( "Close", callback::onCloseSelectedEditors ) );
		menuManager.add( closeMenuItem );
	}

	@Override
	public void menuAboutToShow(IMenuManager manager) {

		//TODO should I just add the ones I want or set visibility and add all?
		pinMenuItem.setVisible( callback.canPinSelectedEditors() );
		unPinMenuItem.setVisible( callback.canUnPinSelectedEditors() );

		menuManager.add( pinMenuItem );
		menuManager.add( unPinMenuItem );
		menuManager.add( closeMenuItem );
	}

	public Menu createContextMenu(Control parent) {
		return menuManager.createContextMenu( parent );
	}

	private static class EditorItemMenuAction extends Action {

		Runnable runnable;

		EditorItemMenuAction(String title, Runnable runnable) {
			this.runnable = runnable;
			setText( title );
		}

		@Override
		public void run() {
			runnable.run();
		}
	}

}
