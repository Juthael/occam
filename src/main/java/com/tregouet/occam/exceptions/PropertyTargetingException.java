package com.tregouet.occam.exceptions;

public class PropertyTargetingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5625279503161382062L;

	public PropertyTargetingException() {
	}

	public PropertyTargetingException(String message) {
		super(message);
	}

	public PropertyTargetingException(Throwable cause) {
		super(cause);
	}

	public PropertyTargetingException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyTargetingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
