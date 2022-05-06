package com.epam.cms.controllers;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cms.config.CurrentUser;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.exceptions.UnAuthorizedAccessException;
import com.epam.cms.services.CourseService;
import com.epam.cms.services.InstructorService;


@RestController
@RequestMapping("/courses")
public class CourseRestController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private CurrentUser currentUser;
	
	@GetMapping()
	public ResponseEntity<List<CourseDto>> coursesList(){
		return  ResponseEntity.ok().body(courseService.findAll().stream()
				.filter(course -> course.getInstructor().getUsername().equals(currentUser.getUsername())).collect(Collectors.toList()));
	}
	
	@PostMapping
	public ResponseEntity<CourseDto> createCourse(@RequestBody @Valid CourseDto courseDto){
		courseDto.setInstructor(instructorService.findByUsername(currentUser.getUsername()));
		return new ResponseEntity<CourseDto>(courseService.save(courseDto),HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<CourseDto> updateCourse(@RequestBody @Valid CourseDto courseDto){
		if(courseService.findById(courseDto.getCourseId()).get().getInstructor().getUsername().equals(currentUser.getUsername())==false) {
			throw new UnAuthorizedAccessException("UnAuthorized Access ");
		}
		courseDto.setInstructor(instructorService.findByUsername(currentUser.getUsername()));
		return new ResponseEntity<CourseDto>(courseService.update(courseDto),HttpStatus.OK);
	}
	
	@GetMapping("/{courseid}")
	public ResponseEntity<CourseDto> viewCourse(@PathVariable("courseid") int courseId) {
	
		if(courseService.findById(courseId).get().getInstructor().getUsername().equals(currentUser.getUsername())==false) {
			throw new UnAuthorizedAccessException("UnAuthorized Access ");
		}
		return new ResponseEntity<CourseDto>(courseService.findById(courseId).get(),HttpStatus.OK);
	}
	
	@DeleteMapping("/{courseid}")
	public ResponseEntity<String> deleteCourse(@PathVariable("courseid") int courseId){
		if(courseService.findById(courseId).get().getInstructor().getUsername().equals(currentUser.getUsername())==false) {
			throw new UnAuthorizedAccessException("UnAuthorized Access ");
		}
		courseService.delete(courseId);
		return new ResponseEntity<String>("course Deleted successfully",HttpStatus.NO_CONTENT);
	}
	
}
