package com.epam.cms.controllers;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cms.dto.InstructorDto;
import com.epam.cms.services.InstructorService;

@RestController
@RequestMapping("/register")
public class RegisterRestController {
	
	@Autowired
	private InstructorService instructorService;
	
	@PostMapping
	public ResponseEntity<InstructorDto> authenticate(@RequestBody @Valid InstructorDto instructorDto){
		return new ResponseEntity<InstructorDto>(instructorService.save(instructorDto).get(),HttpStatus.CREATED);
	}
	
}

