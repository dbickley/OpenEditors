package com.deepnoodle.openeditors.logging;

import java.io.PrintStream;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.ILogListener;
import org.eclipse.core.runtime.IStatus;
import org.osgi.framework.Bundle;

/**
 * ILog implementation that does not require the plug-in activator.
 * This can be usedin unit tests for example.
 */
public class LogToStdStream implements ILog {

	@Override
	public void log(IStatus status) {
		try( PrintStream printStream = ( status.getSeverity() == IStatus.ERROR ) ? System.err : System.out; ) {
			printStream.println( status.getMessage() );
			if( status.getException() != null ) {
				printStream.println( status.getException() );
			}
		}
	}

	@Override
	public void addLogListener(ILogListener arg0) {

	}

	@Override
	public void removeLogListener(ILogListener arg0) {

	}

	@Override
	public Bundle getBundle() {
		return null;
	}

}