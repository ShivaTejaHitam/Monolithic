/*package com.epam.cms.controllers;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
public class LoginController {

	@Autowired
	private InstructorService loginService;

	@GetMapping("logins")
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("instructorDto", new InstructorDto());
		modelAndView.setViewName("Login");
		return modelAndView;
	}

	@PostMapping(value = "logins")
	public ModelAndView login(@Valid @ModelAttribute InstructorDto instructorDto, BindingResult result,
			HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Login");
		
		 if(!result.hasErrors()) {
			Optional<InstructorDto> instructor = loginService.authenticate(instructorDto);
			if (instructor.isPresent()) {
				request.getSession().setAttribute("instructorSession", instructor.get());
				modelAndView.setViewName("Index");
			}
		}
		 
		return modelAndView;
	}

	@GetMapping("logout")
	public ModelAndView logout(HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		session.removeAttribute("instructorSession");
		modelAndView.addObject("instructorDto", new InstructorDto());
		modelAndView.setViewName("Login");
		return modelAndView;
	}
	

}
*/
