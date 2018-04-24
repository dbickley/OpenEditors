package com.deepnoodle.openeditors.views.openeditors;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.deepnoodle.openeditors.models.editor.IEditor;

class EditorViewLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public String getColumnText(Object obj, int index) {
		return getText(obj);
	}

	@Override
	public Image getColumnImage(Object obj, int index) {
		return getImage(obj);
	}

	@Override
	public Image getImage(Object obj) {
		Image image = null;
		if (obj instanceof IEditor) {
			IEditor editor = ((IEditor) obj);
			image = editor.getTitleImage();
			if (image == null) {
				//Load the image for the file
				image = PlatformUI.getWorkbench().getEditorRegistry()
						.getImageDescriptor(editor.getFilePath())
						.createImage();
			}
		}

		//Default to file image if none found
		if (image == null) {
			image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
		return image;
	}

}