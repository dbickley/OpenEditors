package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.deepnoodle.openeditors.logging.LogWrapper;
import com.deepnoodle.openeditors.models.EditorComparator.SortType;
import com.deepnoodle.openeditors.ui.dnd.ReorderViewerDragAndDropSupport;

public class EditSortSequenceDialog extends Dialog {
	@SuppressWarnings("unused")
	private static LogWrapper log = new LogWrapper( EditSortSequenceDialog.class );

	private TableViewer tableViewer;

	private List<SortType> sortTypes;

	public EditSortSequenceDialog(Shell parentShell, List<SortType> sortTypes) {
		super( parentShell );
		this.sortTypes = sortTypes;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea( parent );

		Label label = new Label( composite, SWT.NONE );
		label.setText( "Use Drag and Drop to reorder the sort sequence." );

		tableViewer = new TableViewer( composite, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL );
		tableViewer.setLabelProvider( new SortTypeLabelProvider() );
		tableViewer.setContentProvider( ArrayContentProvider.getInstance() );
		tableViewer.setInput( sortTypes );

		GridDataFactory.fillDefaults().grab( true, true ).applyTo( tableViewer.getControl() );

		ReorderViewerDragAndDropSupport.enableReorderViaDragAndDrop( tableViewer );

		return composite;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell( newShell );
		// Set custom title
		newShell.setText( "Sort Sequence for Open Editors" );
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	@Override
	protected Point getInitialSize() {
		return new Point( 450, 300 );
	}

	public List<SortType> getSortSequence() {
		return sortTypes;
	}

	private static class SortTypeLabelProvider extends ColumnLabelProvider {

		@Override
		public String getText(Object element) {
			SortType sortType = (SortType) element;
			switch( sortType ) {
			case NAME :
				return "File name";
			case PATH :
				return "File path";
			case EXTENSION :
				return "File Extension";
			case NATURAL :
				return "Tab Order in Eclipse";
			case ACCESS :
				return "Last access time";
			default :
				return sortType.toString();
			}
		}
	}
}
