package com.deepnoodle.openeditors.ui.dnd;

import static com.deepnoodle.openeditors.utils.ListUtils.moveTo;

import java.util.List;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerDropAdapter;
import org.eclipse.swt.dnd.TransferData;

import com.deepnoodle.openeditors.logging.LogWrapper;

/**
 * A DropTargetListener that reorders an SWT viewer with a List as input.
 * The DragSourceListener must store the index of an element as String for this drop target to work.
 * The element at this position will then be moved to the target location of the drop.
 * <br>
 * For this to work, the Transfer types of the DragSource must support TextTransfer.getInstance().
 * 
 * @author andreas
 * @see {@link ReorderViewerDragSource}.
 */
public class ReorderViewerDropTarget extends ViewerDropAdapter {
	private static LogWrapper log = new LogWrapper( ReorderViewerDropTarget.class );

	public ReorderViewerDropTarget(Viewer viewer) {
		super( viewer );
		setSelectionFeedbackEnabled( false );
	}

	@Override
	public boolean validateDrop(Object target, int operation, TransferData transferType) {
		return true;
	}

	@Override
	public boolean performDrop(Object data) {
		// The data must contain the source position as a String.
		var sourceIndex = Integer.valueOf( data.toString() );

		// Get the target position.
		var target = getCurrentTarget();
		if( target == null ) {
			return false;
		}
		var list = ( (List<?>) getViewer().getInput() );
		double targetIndex = list.indexOf( target );
		if( targetIndex < 0 ) {
			log.warn( "Drop target not found in list (target:" + target + ", list: " + list + ")" );
			return false;
		}

		// Do nothing if target and source are the same
		if( targetIndex == sourceIndex ) {
			return false;
		}

		// Only allow dropping items after or before other items but not on other items.
		var dropLocation = getCurrentLocation();
		if( dropLocation == ViewerDropAdapter.LOCATION_BEFORE ) {
			targetIndex -= 0.5;
			targetIndex = Math.max( targetIndex, 0 );
		} else if( dropLocation == ViewerDropAdapter.LOCATION_AFTER ) {
			targetIndex += 0.5;
			targetIndex = Math.min( targetIndex, list.size() - 1 );
		} else {
			return false;
		}

		// Map the position between elements to a valid index in the list for reordering.
		if( targetIndex > sourceIndex ) {
			targetIndex = Math.floor( targetIndex );
		} else {
			targetIndex = Math.ceil( targetIndex );
		}

		// Reorder and refresh viewer.
		moveTo( list, sourceIndex, (int) targetIndex );
		getViewer().refresh();
		return true;
	}
}
