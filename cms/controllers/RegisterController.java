package com.epam.cms.controllers;

import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.cms.dto.InstructorDto;
import com.epam.cms.services.InstructorService;

@Controller
public class RegisterController {
	
	@Autowired
	private InstructorService instructorService;

	@GetMapping("registers")
	public ModelAndView register() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("instructorDto", new InstructorDto());
		modelAndView.setViewName("Register");
		return modelAndView;
	}

	@PostMapping(value = "registers")
	public ModelAndView register(@Valid @ModelAttribute InstructorDto instructorDto, BindingResult result) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Register");

		if (!result.hasErrors()) {
			instructorService.save(instructorDto);
			modelAndView.setViewName("Login");
		}
		return modelAndView;
	}

}
