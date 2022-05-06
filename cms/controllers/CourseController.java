package com.epam.cms.controllers;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.epam.cms.config.CurrentUser;
import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.exceptions.CourseNotFoundException;
import com.epam.cms.services.AssignmentService;
import com.epam.cms.services.CourseService;
import com.epam.cms.services.InstructorService;


@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;

	@Autowired
	private AssignmentService assignmentService;
	
	@Autowired
	private InstructorService instructorService;
	
	@Autowired
	private CurrentUser currentUser;

	private ModelAndView modelAndView = new ModelAndView();

	@RequestMapping("/coursesList")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ModelAndView coursesList() {

		List<CourseDto> courses = courseService.findAll().stream()
				.filter(course -> course.getInstructor().getUsername().equals(currentUser.getUser())).collect(Collectors.toList());
		modelAndView.addObject("courses", courses);
		modelAndView.setViewName("CoursesList");
		return modelAndView;
	}

	@RequestMapping("/viewCourse/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ModelAndView viewCourse(@PathVariable("id") int courseId) {
		try {
			CourseDto course = courseService.findById(courseId).get();
			List<AssignmentDto> assignments = assignmentService.findAll().stream()
					.filter(assignment -> assignment.getCourse().getCourseId() == courseId).collect(Collectors.toList());
			modelAndView.addObject("course", course);
			modelAndView.addObject("assignments", assignments);
			modelAndView.setViewName("ViewCourse");
		}
		catch(CourseNotFoundException exception) {
			modelAndView.setViewName("redirect:/coursesList");
		}
		
		return modelAndView;
		
	}

	@RequestMapping("/deleteCourse/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteCourse(@PathVariable("id") int courseId) {
		try {
			courseService.delete(courseId);
		}
		catch(CourseNotFoundException exception) {
			
		}
		return "redirect:/coursesList";
	}

	@GetMapping("createCourse")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView createCourse() {
		modelAndView.addObject("courseDto", new CourseDto());
		modelAndView.setViewName("CreateCourse");
		return modelAndView;
	}

	@PostMapping(value = "createCourse")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String createCourse(@Valid @ModelAttribute CourseDto courseDto, BindingResult result) {
		
		String url = "redirect:/createCourse";
		
		if (!result.hasErrors()) {
			
			courseDto.setInstructor(instructorService.findByUsername(currentUser.getUser()));
			courseDto.setAssignments(new ArrayList<AssignmentDto>());			
			courseService.save(courseDto);
			url = "redirect:/coursesList";
		}
			
		return url;
	}
	
	@RequestMapping("/updateCourse/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView updateCourse(@PathVariable("id") int courseId) {

		try {
			CourseDto courseDto = courseService.findById(courseId).get();
			modelAndView.addObject("courseDto", courseDto);
			modelAndView.setViewName("UpdateCourse");
			
		}
		catch(CourseNotFoundException exception) {
			modelAndView.setViewName("redirect:/coursesList");
		}
		return modelAndView;
	}

	@PostMapping(value = "updateCourse")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String updateCourse(@Valid @ModelAttribute CourseDto courseDto, BindingResult result) {

		String url = "/updateCourse/" + courseDto.getCourseId();
		
		if (!result.hasErrors()) {
			courseService.update(courseDto);
			url = "redirect:/coursesList";
		}
		
		return url;
	}
}
