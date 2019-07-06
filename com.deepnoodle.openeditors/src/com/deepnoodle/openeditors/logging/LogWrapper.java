package com.deepnoodle.openeditors.logging;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import com.deepnoodle.openeditors.Activator;

public class LogWrapper {
	private ILog log;
	private Class<?> clazz;

	public LogWrapper(Class<?> clazz) {
		if( Activator.getDefault() != null ) {
			log = Activator.getDefault().getLog();
		} else {
			log = new LogToStdStream();
		}
		this.clazz = clazz;
	}

	public void info(Exception e) {
		info( e, e.getMessage() );
	}

	public void info(String message, Object... messageVariables) {
		info( null, message, messageVariables );
	}

	public void info(Exception e, String message, Object... messageVariables) {
		if( messageVariables != null && messageVariables.length > 0 ) {
			message = String.format( message, messageVariables );
		}
		Status status = new Status( IStatus.INFO, Activator.PLUGIN_ID, clazz.getName() + ":" + message, e );
		log.log( status );
	}

	public void warn(Exception e) {
		warn( e, e.getMessage() );
	}

	public void warn(String message, Object... messageVariables) {
		warn( null, message, messageVariables );
	}

	public void warn(Exception e, String message, Object... messageVariables) {
		if( messageVariables != null && messageVariables.length > 0 ) {
			message = String.format( message, messageVariables );
		}
		Status status = new Status( IStatus.WARNING, Activator.PLUGIN_ID, clazz.getName() + ":" + message, e );
		log.log( status );
	}

	public void error(Exception e) {
		error( e, e.getMessage() );
	}

	public void error(String message, Object... messageVariables) {
		error( null, message, messageVariables );
	}

	public void error(Exception e, String message, Object... messageVariables) {
		if( messageVariables != null && messageVariables.length > 0 ) {
			message = String.format( message, messageVariables );
		}
		Status status = new Status( IStatus.ERROR, Activator.PLUGIN_ID, clazz.getName() + ":" + message, e );
		log.log( status );
	}

}
