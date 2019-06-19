package com.deepnoodle.openeditors.views.openeditors;

import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISaveablePart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.editor.EditorComparator;
import com.deepnoodle.openeditors.models.editor.EditorComparator.SortType;
import com.deepnoodle.openeditors.models.editor.IEditor;
import com.deepnoodle.openeditors.services.EditorService;
import com.deepnoodle.openeditors.services.SettingsService;

public class EditorTableView implements MouseListener, IPropertyListener {
	
	private static LogWrapper log = new LogWrapper(EditorTableView.class);

	/**
	 * Value of MouseEvent.button that represents a left mouse button.
	 */
	private static final int LEFT_MOUSE_BUTTON = 1;
	
	/**
	 * Value of MouseEvent.button that represents a middle mouse button.
	 */
	private static final int MIDDLE_MOUSE_BUTTON = 2;
	
	private SettingsService settingsService = SettingsService.getInstance();

	private EditorRowFormatter editorRowFormatter = EditorRowFormatter.getInstance();

	private EditorService openEditorService = EditorService.getInstance();

	private TableViewer tableViewer;
	private IWorkbenchPartSite site;

	private EditorComparator editorComparator;

	private IEditor activeEditor;
	
	private EditorItemMenuManager menuManager;

	public EditorTableView(Composite parent, IWorkbenchPartSite site, IViewSite iViewSite) {
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.site = site;

		//Build sorter
		SortType sortBy = SortType.ACCESS;
		editorComparator = new EditorComparator(sortBy);
		tableViewer.setComparator(editorComparator);

		tableViewer.setContentProvider(new EditorViewContentProvider());
		tableViewer.setLabelProvider(new EditorViewLabelProvider());
		tableViewer.setInput(iViewSite);

		tableViewer.getControl().addMouseListener(this);
		
		menuManager = new EditorItemMenuManager(this, site, parent);
		tableViewer.getTable().setMenu(menuManager.createContextMenu(parent));

	}

	public void refresh() {
		try {
			if (tableViewer != null
					&& tableViewer.getControl() != null
					&& !tableViewer.getControl().isDisposed()) {
				tableViewer.refresh();
				TableItem[] items = tableViewer.getTable().getItems();
				tableViewer.setSelection(StructuredSelection.EMPTY);
				editorRowFormatter.formatRows(items, activeEditor, tableViewer.getTable().getForeground(),
						tableViewer.getTable().getBackground());
			}
		} catch (Exception e) {
			log.warn(e);
		}
	}
	
	public void setSortBy(EditorComparator.SortType sortBy) {
		editorComparator.setSortBy(sortBy);
		settingsService.getActiveEditorSettingsSet().setSortBy(sortBy);
		refresh();
	}

	@SuppressWarnings("unchecked")
	public List<IEditor> getSelections() {
		return tableViewer.getStructuredSelection().toList();
	}

	public IEditor getSelection() {
		return (IEditor) tableViewer.getStructuredSelection().getFirstElement();
	}

	public EditorComparator getSorter() {
		return editorComparator;
	}

	public Control getTable() {
		return tableViewer.getTable();
	}

	public void setActivePart(IWorkbenchPart activePart) {
		if(activePart == null) {
			setActiveEditor(null);
			return;
		}
		
		TableItem[] items = tableViewer.getTable().getItems();
		for (TableItem item : items) {
			IEditor editor = ((IEditor) item.getData());
			if (editor.isOpened()
					&& editor.getReference() != null
					&& editor.getReference().getPart(false) == activePart) {
				setActiveEditor(editor);
			}
		}
	}

	private void setActiveEditor(IEditor editor) {
		if(activeEditor != null) {
			activeEditor.getReference().removePropertyListener(this);
		}
		activeEditor = editor;
		if(activeEditor != null) {
			activeEditor.getReference().addPropertyListener(this);
		}
	}
	
	public IWorkbenchPartSite getSite() {
		return site;
	}

	@Override
	public void propertyChanged(Object source, int eventCode) {
		switch(eventCode) {
		case ISaveablePart.PROP_DIRTY : {
			onEditorDirtyChanged(source);
			break;
		}
		}
	}
	
	private void onEditorDirtyChanged(Object source) {
		refresh();
	}
	
	public void dispose() {
		// Remove listener on active editor
		setActiveEditor(null);
	}
	
	public void mouseDoubleClick(MouseEvent e) {
		// Open file with double left-mouse-button.
		if(e.button != LEFT_MOUSE_BUTTON) {
			return;
		}
		
		IEditor clickedEditor = getClickedEditor(e);
		if(clickedEditor != null) {
			openEditor(clickedEditor);
		}
	}

	@Override
	public void mouseDown(MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// Open file with left-mouse-button.
		// Close file with middle-mouse-button.
		switch(e.button) {
			case LEFT_MOUSE_BUTTON : {
				IEditor clickedEditor = getClickedEditor(e);
				if(clickedEditor != null) {
					openEditor(clickedEditor);
				}
				break;
			}
			
			case MIDDLE_MOUSE_BUTTON : {
				IEditor clickedEditor = getClickedEditor(e);
				if(clickedEditor != null) {
					closeEditor(clickedEditor);
				}
				break;
			}
		}
	}

	private IEditor getClickedEditor(MouseEvent e) {
		Point clickedPoint = new Point(e.x, e.y);
		ViewerCell clickedCell = tableViewer.getCell(clickedPoint);
		if(clickedCell == null) {
			return null;
		}
		IEditor clickedEditor = (IEditor) clickedCell.getElement();
		return clickedEditor;
	}
	
	private void closeEditor(IEditor editor) {
		try {
			openEditorService.closeEditor(editor, site);
		} catch (Exception e) {
			log.warn(e);
		}
	}

	private void openEditor(IEditor editor) {
		try {
			openEditorService.openEditor(editor, site);
		} catch (Exception e) {
			log.warn(e);
		}
	}
}
