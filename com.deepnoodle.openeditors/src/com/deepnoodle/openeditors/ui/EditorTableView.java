package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorModel;
import com.deepnoodle.openeditors.ui.EditorItemMenuManager.IEditorItemMenuManagerCallback;

public class EditorTableView implements MouseListener, IEditorTableView {

	@SuppressWarnings("unused")
	private static LogWrapper log = new LogWrapper( EditorTableView.class );

	public interface IEditorTableViewPresenter extends IEditorItemMenuManagerCallback {
		void closeEditor(EditorModel editor);

		void openEditor(EditorModel editor);

		EditorModel getActiveEditor();

		void setView(IEditorTableView view);

		List<EditorModel> getSortedEditors();

		void onEditorContentProviderGetElementsCalled(List<EditorModel> editors);

		void addOpenEditorsChangedListener(IOpenEditorsChangedListener listener);

		void removeOpenEditorsChangedListener(IOpenEditorsChangedListener listener);
	}

	/**
	 * Value of MouseEvent.button that represents a left mouse button.
	 */
	private static final int LEFT_MOUSE_BUTTON = 1;

	/**
	 * Value of MouseEvent.button that represents a middle mouse button.
	 */
	private static final int MIDDLE_MOUSE_BUTTON = 2;

	private IEditorTableViewPresenter presenter;

	private IViewSite viewSite;

	private TableViewer tableViewer;

	private EditorItemMenuManager menuManager;

	public EditorTableView(Composite parent, IViewSite viewSite, IEditorTableViewPresenter presenter) {
		this.viewSite = viewSite;
		this.presenter = presenter;
		presenter.setView( this );
		tableViewer = new TableViewer( parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL );

		var editorLabelProvider = new EditorViewLabelProvider( presenter );
		var editorContentProvider = new EditorContentProvider( presenter );
		presenter.addOpenEditorsChangedListener( editorLabelProvider );
		tableViewer.setLabelProvider( editorLabelProvider );
		tableViewer.setContentProvider( editorContentProvider );
		// TODO: can I set null as input?
		tableViewer.setInput( viewSite );

		// Enable tool tips
		ColumnViewerToolTipSupport.enableFor( tableViewer );

		tableViewer.getControl().addMouseListener( this );

		menuManager = new EditorItemMenuManager( presenter, viewSite, parent );
		tableViewer.getTable().setMenu( menuManager.createContextMenu( parent ) );
	}

	@Override
	public void refresh() {
		tableViewer.refresh();
	}

	private void formatRows(TableItem[] items, EditorModel activeEditor) {
		//				for( TableItem item : items ) {
		//					try {
		//						EditorModel editor = ( (EditorModel) item.getData() );
		//						if( editor.isPinned() ) {
		//							item.setForeground( pinnedColor );
		//						} else if( editor.isDirty() ) {
		//							item.setForeground( dirtyColor );
		//						} else {
		//							item.setForeground( forgroundColor );
		//						}
		//		
		//						if( activeEditor != null && editor.getFilePath().equals( activeEditor.getFilePath() ) ) {
		//							item.setBackground( highlightColor );
		//						} else {
		//							item.setBackground( backgroundColor );
		//						}
		//						item.setChecked( false );
		//					} catch( Exception e ) {
		//						log.warn( e );
		//					}
		//				}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<EditorModel> getSelectedEditors() {
		return tableViewer.getStructuredSelection().toList();
	}

	public EditorModel getSelection() {
		return (EditorModel) tableViewer.getStructuredSelection().getFirstElement();
	}

	public Control getTable() {
		return tableViewer.getTable();
	}

	public IWorkbenchPartSite getSite() {
		return viewSite;
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// Open file with double left-mouse-button.
		if( e.button != LEFT_MOUSE_BUTTON ) {
			return;
		}

		EditorModel clickedEditor = getClickedEditor( e );
		if( clickedEditor != null ) {
			presenter.openEditor( clickedEditor );
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
		switch( e.button ) {
		case LEFT_MOUSE_BUTTON : {
			EditorModel clickedEditor = getClickedEditor( e );
			if( clickedEditor != null ) {
				presenter.openEditor( clickedEditor );
			}
			break;
		}

		case MIDDLE_MOUSE_BUTTON : {
			EditorModel clickedEditor = getClickedEditor( e );
			if( clickedEditor != null ) {
				presenter.closeEditor( clickedEditor );
			}
			break;
		}
		}
	}

	@Override
	public IViewSite getViewSite() {
		return viewSite;
	}

	private EditorModel getClickedEditor(MouseEvent e) {
		Point clickedPoint = new Point( e.x, e.y );
		ViewerCell clickedCell = tableViewer.getCell( clickedPoint );
		if( clickedCell == null ) {
			return null;
		}
		EditorModel clickedEditor = (EditorModel) clickedCell.getElement();
		return clickedEditor;
	}

}
