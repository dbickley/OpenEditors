package com.deepnoodle.openeditors.ui.dnd;

import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.widgets.Table;

/**
 * A DragSourceListener that reorders an SWT viewer with a List as input.
 * The index of the dragged element is stored as a String.
 * <br>
 * For this to work, the Transfer types of the DragSource must support TextTransfer.getInstance().
 * 
 * @author andreas
 * @see {@link ReorderViewerDropTarget}.
 */
public class ReorderViewerDragSource extends DragSourceAdapter {

	@Override
	public void dragSetData(DragSourceEvent event) {
		// Store the selected item index
		DragSource ds = (DragSource) event.widget;
		Table table = (Table) ds.getControl();
		int selectionIndex = table.getSelectionIndex();
		event.data = String.valueOf( selectionIndex );
	}
}
