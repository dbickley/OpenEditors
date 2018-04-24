package com.deepnoodle.openeditors.views;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class DialogUtils {
	public static Shell getScreenCentredShell() {
		Display display = PlatformUI.getWorkbench().getDisplay();
		Shell centreShell = new Shell(display);
		Point size = centreShell.computeSize(-1, -1);
		Rectangle screen = display.getMonitors()[0].getBounds();
		centreShell.setBounds((screen.width - size.x) / 2, (screen.height - size.y) / 2, size.x, size.y);
		return centreShell;
	}
}
