package com.deepnoodle.openeditors.services;

import static com.deepnoodle.openeditors.utils.ListUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorModel;
import com.deepnoodle.openeditors.utils.IndexedEntry;
import com.deepnoodle.openeditors.utils.ListUtils;

/**
 * Bridge between editors of the Eclipse platform and the corresponding model object of the OpenEditors plug-in.
 */
public class EclipseEditorService {
	private static LogWrapper log = new LogWrapper( EclipseEditorService.class );

	private List<EditorModel> openEditors = new ArrayList<>();

	/**
	 * Creates a list of EditorModels from the currently open Eclipse editors.
	 * @return
	 */
	public List<EditorModel> getOpenEditors() {
		List<EditorModel> lastOpenEditors = copy( openEditors );
		openEditors.clear();

		try {
			// Loop through open editors in Eclipse workbench.
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IEditorReference[] references = activePage.getEditorReferences();
			for( IndexedEntry<IEditorReference> entryWithIndex : indexed( references ) ) {
				IEditorReference reference = entryWithIndex.getEntry();

				// Find existing EditorModel or create a new EditorModel if none yet.
				EditorModel editor = ListUtils.findFirst( lastOpenEditors, (lastEditor) -> {
					return ( reference == lastEditor.getReference() );
				} ).orElse( new EditorModel( reference ) );

				// Update the data of the editor
				editor.setNaturalPosition( entryWithIndex.getIndex() );
				editor.setDirty( reference.isDirty() );
				String filePath = getFilePath( reference );
				editor.setFilePath( filePath );
				editor.setName( reference.getName() );

				openEditors.add( editor );
			}
		} catch( Exception e ) {
			log.error( e );
		}

		return openEditors;
	}

	/**
	 * Opens the Eclipse editor that is associated with the EditorModel.
	 */
	public void openEditor(EditorModel editor, IWorkbenchPartSite site) {
		try {
			IEditorReference reference = editor.getReference();
			site.getWorkbenchWindow().getActivePage().openEditor( reference.getEditorInput(), reference.getId() );
		} catch( Exception e ) {
			log.error( e, "Could not open editor: %s", editor.getName() );
		}
	}

	/**
	 * Closes the Eclipse editor that is associated with the EditorModel.
	 */
	public void closeEditor(EditorModel editor, IWorkbenchPartSite site) {
		try {
			IEditorPart iEditorPart = editor.getReference().getEditor( true );
			if( iEditorPart != null ) {
				site.getWorkbenchWindow().getActivePage().closeEditor( iEditorPart, true );
			}
		} catch( Exception e ) {
			log.error( e, "Could not close editor: %s", editor.getName() );
		}
	}

	private String getFilePath(IEditorReference reference) {
		String path = null;
		try {
			if( reference.getEditorInput() != null && reference.getEditorInput() instanceof FileEditorInput ) {
				FileEditorInput fileEditorInput = ( (FileEditorInput) reference.getEditorInput() );
				if( fileEditorInput.getFile() != null ) {
					path = fileEditorInput.getFile().getFullPath().toString();
				}
			}
		} catch( Exception e ) {
			log.warn( e, "Problem getting filepath" );
		}
		return path;
	}
}
