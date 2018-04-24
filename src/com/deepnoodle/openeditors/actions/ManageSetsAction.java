package com.deepnoodle.openeditors.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.services.SettingsService;
import com.deepnoodle.openeditors.views.DialogUtils;
import com.deepnoodle.openeditors.views.ManageSets.ManageSetsView;
import com.deepnoodle.openeditors.views.openeditors.EditorSetComboControl;

public class ManageSetsAction extends Action {
	//private static LogWrapper log = new LogWrapper(EditorTableView.class);

	EditorService editorService = EditorService.getInstance();
	SettingsService settingsService = SettingsService.getInstance();

	private EditorSetComboControl editorSetComboControl;

	public ManageSetsAction(EditorSetComboControl editorSetComboControl) {
		this.editorSetComboControl = editorSetComboControl;
		setText("Preferences");
		setToolTipText("Preferences");
		setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_DEF_VIEW));
	}

	@Override
	public void run() {
		ManageSetsView dialog = new ManageSetsView(DialogUtils.getScreenCentredShell(), editorSetComboControl);
		dialog.create();
		dialog.open();
	}
}
