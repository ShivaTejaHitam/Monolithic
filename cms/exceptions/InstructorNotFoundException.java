package com.epam.cms.exceptions;

public class InstructorNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2854109633877289635L;
	
	public InstructorNotFoundException(String message) {
		super(message);
	}
	
}

