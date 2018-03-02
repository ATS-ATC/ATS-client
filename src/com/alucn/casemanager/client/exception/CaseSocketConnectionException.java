package com.alucn.casemanager.client.exception;

public class CaseSocketConnectionException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8314050272818472078L;

	public CaseSocketConnectionException() {
		super();
	}

	public CaseSocketConnectionException(String message) {
		super(message);
	}
	
	public CaseSocketConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
