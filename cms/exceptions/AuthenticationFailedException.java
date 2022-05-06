package com.epam.cms.exceptions;

public class AuthenticationFailedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3499064742130185555L;

	public AuthenticationFailedException(String message) {
		super(message);
	}
}
