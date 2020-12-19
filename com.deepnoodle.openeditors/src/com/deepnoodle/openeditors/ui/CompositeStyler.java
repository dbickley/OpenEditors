package com.deepnoodle.openeditors.ui;

import java.util.List;

import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.TextStyle;

public class CompositeStyler extends Styler {

	private Styler[] stylers;

	public CompositeStyler(List<Styler> stylers) {
		this( stylers.toArray( new Styler[0] ) );
	}

	public CompositeStyler(Styler... stylers) {
		this.stylers = stylers;
	}

	@Override
	public void applyStyles(TextStyle textStyle) {
		for( Styler styler : stylers ) {
			styler.applyStyles( textStyle );
		}
	}

}
