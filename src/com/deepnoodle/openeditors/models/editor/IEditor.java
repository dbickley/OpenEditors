package com.deepnoodle.openeditors.models.editor;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IEditorReference;

public interface IEditor {

	String getFilePath();

	void setFilePath(String path);

	String getName();

	void setName(String name);

	boolean isPinned();

	void setPinned(boolean pinned);

	Integer getNaturalPosition();

	void setNaturalPosition(Integer naturalPosition);

	boolean isOpened();

	void setOpened(boolean opened);

	Integer getHistoryPosition();

	void setHistoryPosition(Integer historyPosition);

	boolean isDirty();

	IEditorReference getReference();

	void setReference(IEditorReference reference);

	String getTitleImagePath();

	void setTitleImagePath(String titleImage);

	Image getTitleImage();

	void setTitleImage(Image titleImage);

}
