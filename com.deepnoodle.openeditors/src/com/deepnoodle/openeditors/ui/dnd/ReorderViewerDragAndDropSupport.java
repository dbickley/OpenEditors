package com.deepnoodle.openeditors.ui.dnd;

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;

/**
 * Utility class to configure reordering of elements in a SWT Viewer via Drag and Drop.
 * The Viewer must have an input of type List.
 * @author andre
 *
 */
public final class ReorderViewerDragAndDropSupport {

	private ReorderViewerDragAndDropSupport() {
	}

	public static void enableReorderViaDragAndDrop(Viewer viewer) {
		try {
			@SuppressWarnings("unused")
			List<?> viewerInput = (List<?>) viewer.getInput();
		} catch( ClassCastException e ) {
			throw new IllegalArgumentException(
			    "The viewer must have an input of type List for ReorderViewerDragAndDropSupport." );
		}

		// Create the drag source
		Transfer[] transferTypes = new Transfer[] {TextTransfer.getInstance()};
		DragSource source = new DragSource( viewer.getControl(), DND.DROP_MOVE );
		source.setTransfer( transferTypes );
		source.addDragListener( new ReorderViewerDragSource() );

		// Create the drop target
		DropTarget target = new DropTarget( viewer.getControl(), DND.DROP_MOVE );
		target.setTransfer( transferTypes );
		target.addDropListener( new ReorderViewerDropTarget( viewer ) );
	}

}
