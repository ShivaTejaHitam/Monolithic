/*package com.epam.cms.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.services.InstructorService;

@RestController
@RequestMapping("/login2")
public class LoginRestController {
	
	@Autowired
	private InstructorService loginService;
	
	@GetMapping()
	public ResponseEntity<InstructorDto> authenticate(@RequestBody InstructorDto instructorDto){
		return new ResponseEntity<InstructorDto>(loginService.authenticate(instructorDto).get(),HttpStatus.OK);
	}
	
}
*/
