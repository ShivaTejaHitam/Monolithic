package com.epam.cms.controllers;


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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;

import com.epam.cms.exceptions.AssignmentNotFoundException;
import com.epam.cms.exceptions.CourseNotFoundException;
import com.epam.cms.services.AssignmentService;
import com.epam.cms.services.CourseService;

@Controller
public class AssignmentController {

	@Autowired
	private AssignmentService assignmentService;

	@Autowired
	private CourseService courseService;

	private ModelAndView modelAndView = new ModelAndView();

	@GetMapping("createAssignment/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView createAssignment(@PathVariable("id") int courseId) {
		modelAndView.addObject("assignmentDto", new AssignmentDto());
		modelAndView.addObject("courseId", courseId);
		modelAndView.setViewName("CreateAssignment");
		return modelAndView;
	}
    
	@PostMapping(value = "createAssignment/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String createAssignment(@PathVariable("id") int courseId, @Valid @ModelAttribute AssignmentDto assignmentDto,
			BindingResult result, HttpSession session) {

		String url = "/createAssignment/" + courseId;

		if (!result.hasErrors()) {
			try {
				CourseDto course = courseService.findById(courseId).get();
				assignmentDto.setCourse(course);
				assignmentDto.setInstructor(course.getInstructor());
				assignmentService.save(assignmentDto);
				url = "redirect:/viewCourse/" + courseId;
			} catch (CourseNotFoundException exception) {
				url = "/createAssignment/" + courseId;
			}
		}
		return url;
	}

	@RequestMapping("/viewAssignment/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ModelAndView viewAssignment(@PathVariable("id") int assignmentId) {
		try {
			AssignmentDto assignment = assignmentService.findById(assignmentId).get();
			modelAndView.addObject("assignment", assignment);
			modelAndView.setViewName("ViewAssignment");
		} catch (AssignmentNotFoundException exception) {
			modelAndView.setViewName("redirect:/coursesList");
		}

		return modelAndView;

	}

	@RequestMapping("/deleteAssignment/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String deleteAssignment(@PathVariable("id") int assignmentId, HttpSession session) {

		AssignmentDto assignmentDto = new AssignmentDto();
		
		try {
			assignmentService.delete(assignmentId);
		} catch (AssignmentNotFoundException exception) {
			
		}

		return "redirect:/viewCourse/" + assignmentDto.getCourse().getCourseId();
	}

	@RequestMapping("/u-pdateAssignment/{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView updateAssignment(@PathVariable("id") int assignmentId, HttpSession session) {
		AssignmentDto assignmentDto = assignmentService.findById(assignmentId).get();
		modelAndView.addObject("assignmentDto", assignmentDto);
		modelAndView.setViewName("UpdateAssignment");
		return modelAndView;
	}

	@PostMapping(value = "updateAssignment")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String updateAssignment(@Valid @ModelAttribute AssignmentDto assignmentDto, BindingResult result,
			HttpSession session) {

		String url = "/updateAssignment/" + assignmentDto.getAssignmentId();

		if (!result.hasErrors()) {
			try {
				assignmentService.save(assignmentDto);
				url = "redirect:/viewCourse/" + assignmentDto.getCourse().getCourseId();
			}
			catch(AssignmentNotFoundException exception) {
				url = "/updateAssignment/" + assignmentDto.getAssignmentId();
			}
		}

		return url;
	}
	
}
