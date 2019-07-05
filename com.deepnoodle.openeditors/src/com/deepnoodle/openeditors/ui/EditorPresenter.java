package com.deepnoodle.openeditors.ui;

import static com.deepnoodle.openeditors.utils.ListUtils.copy;

import java.util.List;

import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.EditorPart;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorComparator;
import com.deepnoodle.openeditors.models.EditorModel;
import com.deepnoodle.openeditors.services.EclipseEditorService;
import com.deepnoodle.openeditors.services.SettingsService;
import com.deepnoodle.openeditors.ui.EditorItemMenuManager.IEditorItemMenuManagerCallback;
import com.deepnoodle.openeditors.ui.EditorTableView.IEditorTableViewPresenter;
import com.deepnoodle.openeditors.ui.actions.SortAction.ISortActionCallback;
import com.deepnoodle.openeditors.utils.ListUtils;

public class EditorPresenter
    implements IEditorTableViewPresenter, IEditorItemMenuManagerCallback, ISortActionCallback, IPartListener,
    IPropertyListener {

	private static LogWrapper log = new LogWrapper( EditorPresenter.class );

	private SettingsService settingsService;
	private EclipseEditorService editorService;
	private IEditorTableView view;

	private EditorComparator editorComparator;

	private EditorModel activeEditor;

	public EditorPresenter(EclipseEditorService editorService, SettingsService settingsService) {
		this.editorService = editorService;
		this.settingsService = settingsService;
	}

	@Override
	public void setView(IEditorTableView view) {
		this.view = view;
	}

	@Override
	public void setSortBy(EditorComparator.SortType sortBy) {
		settingsService.editAndSave( (settings) -> settings.setSortBy( sortBy ) );
		getOrCreateEditorComparator().setSortBy( sortBy );
		refresh();
	}

	@Override
	public void openEditor(EditorModel editor) {
		editorService.openEditor( editor, view.getViewSite() );
		refresh();
	}

	@Override
	public void closeEditor(EditorModel editor) {
		editorService.closeEditor( editor, view.getViewSite() );
		refresh();
	}

	@Override
	public void onCloseSelectedEditors() {
		List<EditorModel> editors = view.getSelectedEditors();
		for( var editor : editors ) {
			editorService.closeEditor( editor, view.getViewSite() );
		}
		refresh();
	}

	@Override
	public void onPinSelectedEditors() {
		List<EditorModel> editors = view.getSelectedEditors();
		editors.forEach( (editor) -> editor.setPinned( true ) );
		refresh();
	}

	@Override
	public void onUnPinSelectedEditors() {
		List<EditorModel> editors = view.getSelectedEditors();
		editors.forEach( (editor) -> editor.setPinned( false ) );
		refresh();
	}

	@Override
	public boolean canPinSelectedEditors() {
		var editors = view.getSelectedEditors();
		return ListUtils.findFirst( editors, (editor) -> !editor.isPinned() ).isPresent();
	}

	@Override
	public boolean canUnPinSelectedEditors() {
		var editors = view.getSelectedEditors();
		return ListUtils.findFirst( editors, (editor) -> editor.isPinned() ).isPresent();
	}

	@Override
	public void partActivated(IWorkbenchPart part) {
		if( part instanceof EditorPart ) {
			setActivePart( part );
			refresh();
		}
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if( part instanceof EditorPart ) {
			refresh();
		}
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
		// Do nothing here. partBroughtToTop or partActivated will be called afterwards.
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		if( part instanceof EditorPart ) {
			refresh();
		}

	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
		setActivePart( null );
	}

	@Override
	public EditorModel getActiveEditor() {
		return activeEditor;
	}

	@Override
	public void propertyChanged(Object source, int eventCode) {
		switch( eventCode ) {
		case ISaveablePart.PROP_DIRTY : {
			onEditorDirtyChanged( source );
			break;
		}
		}
	}

	public void dispose() {
		// Remove listener on active editor
		setActiveEditor( null );
	}

	public void refresh() {
		view.refresh();
	}

	private EditorComparator getOrCreateEditorComparator() {
		if( editorComparator == null ) {
			editorComparator = new EditorComparator( settingsService.getSettings().getSortBy() );
		}
		return editorComparator;
	}

	private void onEditorDirtyChanged(Object source) {
		refresh();
	}

	private void setActivePart(IWorkbenchPart activePart) {
		if( activePart == null ) {
			setActiveEditor( null );
			return;
		}

		List<EditorModel> editors = editorService.getOpenEditors();
		for( var editor : editors ) {
			if( editor.getReference() != null && editor.getReference().getPart( false ) == activePart ) {
				setActiveEditor( editor );
			}
		}
	}

	private void setActiveEditor(EditorModel editor) {
		if( activeEditor != null ) {
			activeEditor.getReference().removePropertyListener( this );
		}
		activeEditor = editor;
		if( activeEditor != null ) {
			activeEditor.getReference().addPropertyListener( this );
		}
	}

	@Override
	public List<EditorModel> getSortedEditors() {
		List<EditorModel> editors = editorService.getOpenEditors();
		EditorComparator editorComparator = getOrCreateEditorComparator();
		List<EditorModel> sortedEditors = copy( editors );
		sortedEditors.sort( editorComparator );
		return sortedEditors;
	}
}
