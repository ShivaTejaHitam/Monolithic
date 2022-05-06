package com.epam.cms.exceptions;

public class UnAuthorizedAccessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1444641601991216348L;
	
	public UnAuthorizedAccessException(String message) {
		super(message);
	}

}
