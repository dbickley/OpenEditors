package com.deepnoodle.openeditors.ui;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.deepnoodle.openeditors.models.IEditor;

class EditorViewLabelProvider extends DelegatingStyledCellLabelProvider {

	public EditorViewLabelProvider() {
		super( new EditorStyledLabelProvider() );
	}
	
	@Override
	public Image getImage(Object obj) {
		Image image = null;
		IEditor editor = ((IEditor) obj);
		if(editor.getReference() != null) {
			image = editor.getReference().getTitleImage();
		}

		//Default to file image if none found
		if (image == null) {
			image = PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		}
		return image;
	}
	
	private static class EditorStyledLabelProvider extends LabelProvider implements IStyledLabelProvider {

		@Override
		public String getText(Object obj) {
			IEditor editor = ((IEditor) obj);
			if (editor.isDirty()) {
				return "*"+editor.getName();
			} else {
				return editor.getName();
			}
		}
		
		@Override
		public StyledString getStyledText(Object element) {
			StyledString str = new StyledString(getText(element));
			// Show extra info in lighter color
			// str.append(" - " + "extra info", StyledString.QUALIFIER_STYLER);
			return str;
		}
	}

}