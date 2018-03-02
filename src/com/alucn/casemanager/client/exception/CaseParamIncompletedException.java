package com.alucn.casemanager.client.exception;

public class CaseParamIncompletedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8314050272818472078L;

	public CaseParamIncompletedException() {
		super();
	}

	public CaseParamIncompletedException(String message) {
		super(message);
	}
	
	public CaseParamIncompletedException(String message, Throwable cause) {
		super(message, cause);
	}

}
