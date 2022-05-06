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
import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.services.AssignmentService;

@RestController
@RequestMapping("/courses/{courseid}/assignments")
public class AssignmentRestController {

	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private CurrentUser currentUser;

	@GetMapping()
	public ResponseEntity<List<AssignmentDto>> assignmentsList(@PathVariable("courseid") int courseId) {
		return ResponseEntity
				.ok().body(
						assignmentService.findAll().stream()
								.filter(assignment -> assignment.getInstructor().getUsername().equals(
										currentUser.getUsername()) && assignment.getCourse().getCourseId() == courseId)
								.collect(Collectors.toList()));
	}

	@PostMapping
	public ResponseEntity<AssignmentDto> createAssignment(@RequestBody @Valid AssignmentDto assignmentDto) {
		return new ResponseEntity<AssignmentDto>(assignmentService.save(assignmentDto), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<AssignmentDto> updateAssignment(@RequestBody @Valid AssignmentDto assignmentDto) {
		return new ResponseEntity<AssignmentDto>(assignmentService.update(assignmentDto), HttpStatus.OK);
	}

	@GetMapping("/{assignmentid}")
	public ResponseEntity<AssignmentDto> viewAssignment(@PathVariable("courseid") int courseId,
			@PathVariable("assignmentid") int assignmentId) {
		return new ResponseEntity<AssignmentDto>(assignmentService.findById(assignmentId).get(), HttpStatus.OK);
	}

	@DeleteMapping("/{assignmentid}")
	public ResponseEntity<String> deleteAssignment(@PathVariable("courseid") int courseId,
			@PathVariable("assignmentid") int assignmentId) {
		assignmentService.delete(assignmentId);
		return new ResponseEntity<String>("assignment Deleted successfully", HttpStatus.NO_CONTENT);
	}

}
