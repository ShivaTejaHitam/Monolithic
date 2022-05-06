package com.epam.cms.controllers;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.epam.cms.dto.ExceptionResponse;
import com.epam.cms.exceptions.AssignmentNotFoundException;
import com.epam.cms.exceptions.CourseNotFoundException;
import com.epam.cms.exceptions.InstructorNotFoundException;
import com.epam.cms.exceptions.UnAuthorizedAccessException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(value = CourseNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleCourseNotFoundException(CourseNotFoundException exception,WebRequest request) {
		
		ExceptionResponse exRes = new ExceptionResponse();
		exRes.setError(exception.getMessage());
		exRes.setStatus(HttpStatus.BAD_REQUEST.name());
		exRes.setTimestamp(LocalDate.now().toString());
		exRes.setPath(request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exRes,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = AssignmentNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleAssignmentNotFoundException(AssignmentNotFoundException exception,WebRequest request) {
		
		ExceptionResponse exRes = new ExceptionResponse();
		exRes.setError(exception.getMessage());
		exRes.setStatus(HttpStatus.BAD_REQUEST.name());
		exRes.setTimestamp(LocalDate.now().toString());
		exRes.setPath(request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exRes,HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(value = InstructorNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleInstructorNotFoundException(InstructorNotFoundException exception,WebRequest request) {
		
		ExceptionResponse exRes = new ExceptionResponse();
		exRes.setError(exception.getMessage());
		exRes.setStatus(HttpStatus.BAD_REQUEST.name());
		exRes.setTimestamp(LocalDate.now().toString());
		exRes.setPath(request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exRes,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UnAuthorizedAccessException.class)
	public ResponseEntity<ExceptionResponse> handleUnAuthorizedAccessException(UnAuthorizedAccessException exception,WebRequest request) {
		
		ExceptionResponse exRes = new ExceptionResponse();
		exRes.setError(exception.getMessage());
		exRes.setStatus(HttpStatus.BAD_REQUEST.name());
		exRes.setTimestamp(LocalDate.now().toString());
		exRes.setPath(request.getDescription(false));
		return new ResponseEntity<ExceptionResponse>(exRes,HttpStatus.BAD_REQUEST);
	}
	
}
