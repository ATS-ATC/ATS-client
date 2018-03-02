package com.alucn.casemanager.client.exception;

public class CaseClientException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8314050272818472078L;

	public CaseClientException() {
		super();
	}

	public CaseClientException(String message) {
		super(message);
	}
	
	public CaseClientException(String message, Throwable cause) {
		super(message, cause);
	}

}
