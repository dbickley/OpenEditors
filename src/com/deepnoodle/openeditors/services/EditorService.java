package com.deepnoodle.openeditors.services;

import static com.deepnoodle.openeditors.utils.ListUtils.indexed;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IEditorRegistry;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.editor.IEditor;
import com.deepnoodle.openeditors.models.settings.EditorSettingsModel;
import com.deepnoodle.openeditors.utils.IndexedEntry;

//rename
public class EditorService {
	private static LogWrapper log = new LogWrapper(EditorService.class);

	private SettingsService settingsService = SettingsService.getInstance();
	private static EditorService instance;

	public static EditorService getInstance() {
		if (instance == null) {
			instance = new EditorService();
		}
		return instance;
	}

	public void openEditor(IEditor editor, IWorkbenchPartSite site) throws PartInitException {
		if (editor.getReference() != null) {
			site.getWorkbenchWindow().getActivePage()
					.openEditor(editor.getReference().getEditorInput(), editor.getReference().getId());
		} else {
			openEditor(editor.getFilePath(), site);
		}
	}

	private void openEditor(String filePath, IWorkbenchPartSite site) throws PartInitException {
		IWorkspaceRoot ws = ResourcesPlugin.getWorkspace().getRoot();
		IPath location = new Path(filePath);
		IFile file = ws.getFile(location);

		IEditorDescriptor editorDescriptor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(filePath);
		String editorDescriptorId;
		if (editorDescriptor == null) {
			
			PlatformUI.getWorkbench().getEditorRegistry().findEditor(IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID);
			editorDescriptorId = IEditorRegistry.SYSTEM_EXTERNAL_EDITOR_ID;
		} else {
			editorDescriptorId = editorDescriptor.getId();
		}
		site.getWorkbenchWindow().getActivePage().openEditor(new FileEditorInput(file),
				editorDescriptorId);
	}

	public void closeEditor(IEditor editor, IWorkbenchPartSite site) {
		IEditorPart iEditorPart = editor.getReference().getEditor(true);
		if (iEditorPart != null) {
			site.getWorkbenchWindow().getActivePage()
					.closeEditor(iEditorPart, true);
		}

	}

	private String getFilePath(IEditorReference reference) {
		String path = null;
		try {
			if (reference.getEditorInput() != null && reference.getEditorInput() instanceof FileEditorInput) {
				FileEditorInput fileEditorInput = ((FileEditorInput) reference.getEditorInput());
				path = fileEditorInput.getFile().getFullPath().toString();
			}
		} catch (Exception e) {
			log.warn(e, "Problem getting filepath");
		}
		if (path == null && reference != null) {
			//Last ditch effort to try and get the filename from the tooltip
			try {
				return reference.getEditorInput().getToolTipText();
			} catch (Exception e) {
				log.warn(e, "Problem getting filepath from tooltip");
			}
		}
		return path;
	}

	public List<IEditor> buildOpenEditors() {
		List<IEditor> openEditors = new ArrayList<>();
		
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();

		// Loop through open editors, add them to the set if need be.
		IEditorReference[] references = activePage.getEditorReferences();
		for (IndexedEntry<IEditorReference> entryWithIndex : indexed(references) ) {
			IEditor editor = createEditorFromEclipseEditorReference(entryWithIndex.getEntry());
			editor.setNaturalPosition(entryWithIndex.getIndex());
			openEditors.add(editor);
		}
		
		return openEditors;
	}

	private IEditor createEditorFromEclipseEditorReference(IEditorReference reference) {
		String filePath = getFilePath(reference);
		EditorSettingsModel editor = new EditorSettingsModel(filePath, reference.getName());
		editor.setOpened(true);
		editor.setReference(reference);
		editor.setTitleImage(reference.getTitleImage());
		editor.setTitleImagePath(reference.getTitleImage().getImageData().toString());
		editor.setDirty(reference.isDirty());
		return editor;
	}
	
}
