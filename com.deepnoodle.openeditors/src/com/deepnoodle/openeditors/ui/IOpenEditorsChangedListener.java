package com.deepnoodle.openeditors.ui;

import java.util.List;

import com.deepnoodle.openeditors.models.EditorModel;

public interface IOpenEditorsChangedListener {
	void onOpenEditorsChanged(List<EditorModel> newOpenEditors);

}
