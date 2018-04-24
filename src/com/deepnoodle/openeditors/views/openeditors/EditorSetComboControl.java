package com.deepnoodle.openeditors.views.openeditors;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import com.deepnoodle.openeditors.Constants;
import com.deepnoodle.openeditors.models.editor.IEditor;
import com.deepnoodle.openeditors.models.settings.EditorSetSettingsModel;
import com.deepnoodle.openeditors.models.settings.EditorSettingsModel;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.services.SettingsService;

public class EditorSetComboControl extends ControlContribution {

	private EditorTableView editorTableView;
	private EditorService editorService = EditorService.getInstance();
	private SettingsService settingsService = SettingsService.getInstance();

	protected EditorSetComboControl(EditorTableView editorTableView) {
		super("EditorSets");
		this.editorTableView = editorTableView;
	}

	private Combo combo;

	@Override
	protected Control createControl(Composite parent) {

		combo = new Combo(parent, SWT.READ_ONLY);

		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent selectionEvent) {
				String selectedSetName = combo.getItem(combo.getSelectionIndex());
				if (!selectedSetName.equals(settingsService.getActiveSetName())) {
					EditorSetSettingsModel selectedSet = settingsService.getEditorSettingsSet(selectedSetName);

					if (!selectedSetName.equals(Constants.OPEN_EDITORS_SET_NAME)) {
						Map<String, EditorSettingsModel> editors = settingsService.getActiveEditorSettingsSet()
								.getEditorModels();

						//Generate a list of editors that would be closed
						List<IEditor> toCloseEditors = new LinkedList<>();

						for (IEditor editor : editors.values()) {
							//What to do about pinned?
							if (!selectedSet.getEditorModels().keySet().contains(editor.getFilePath())
									&& editor.isOpened()) {
								toCloseEditors.add(editor);
							}
						}

						//If the to close editor list is not empty, ask about closing
						if (toCloseEditors.size() > 0 && askToCloseExistingEditors() == 0) {
							for (IEditor editor : toCloseEditors) {
								editorService.closeEditor(editor, editorTableView.getSite());
							}
						}
					}
				}
				settingsService.setActiveSetName(selectedSetName);
				editorTableView.setActivePart(null);
				editorTableView.refresh();
			}

		});
		refreshData();
		return combo;

	}

	public void refreshData() {
		Set<String> sets = settingsService.getEditorSettingsSets().keySet();

		String[] setsArray = sets.toArray(new String[sets.size()]);
		Arrays.sort(setsArray);

		combo.setItems(setsArray);

		String activeSetName = settingsService.getActiveSetName();
		combo.setText(activeSetName);

	}

	private int askToCloseExistingEditors() {
		MessageDialog dialog = new MessageDialog(
				null, null, null, "Close open editors or add to selected set?",
				MessageDialog.QUESTION,
				new String[] { "Close Editors", "Add Editors" },
				0);
		int result = dialog.open();
		return result;
	}
}
