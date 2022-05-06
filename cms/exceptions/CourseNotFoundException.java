package com.epam.cms.exceptions;

public class CourseNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6435199431968092076L;	
	
	public CourseNotFoundException(String message) {
		super(message);
	}
	
}
