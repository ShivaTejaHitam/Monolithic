package com.epam.cms.controllers;


import static org.mockito.ArgumentMatchers.any;


import static org.mockito.ArgumentMatchers.anyInt;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cms.config.CurrentUser;
import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.services.AppUserDetailsService;
import com.epam.cms.services.AssignmentService;
import com.epam.cms.services.CourseService;
import com.epam.cms.services.InstructorService;
import com.epam.cms.services.JwtUtil;
import com.epam.cms.exceptions.AuthenticationFailedException;
import com.epam.cms.exceptions.CourseNotFoundException;


@WebMvcTest(CourseController.class)
class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CourseService courseService;
	
	@MockBean
	private AssignmentService assignmentService;
	
	@MockBean
	private InstructorService instructorService;
	
	@MockBean
	private AppUserDetailsService appUserDetailsService;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@MockBean
	private CurrentUser currentUser;

	CourseDto courseDto;
	InstructorDto instructorDto;
	

	@BeforeEach
	void setUp() throws Exception {

		courseDto = new CourseDto();
		courseDto.setCourseId(1);
		courseDto.setCourseName("mock course");
		courseDto.setSyllabus("mocksyllabus");
		instructorDto = new InstructorDto();

		instructorDto.setUsername("ShivaTeja");
		instructorDto.setPassword("password");

		courseDto.setInstructor(instructorDto);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testCoursesList() throws Exception {

		List<CourseDto> courses = new ArrayList<>();
		courses.add(courseDto);
		when(courseService.findAll()).thenReturn(courses);
		when(currentUser.getUser()).thenReturn("ShivaTeja");
		mockMvc.perform(get("/coursesList")).andExpect(view().name("CoursesList"))
				.andExpect(status().isOk()).andExpect(model().attribute("courses", hasSize(1)));
		verify(courseService, times(1)).findAll();
	}

	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testViewCourse() throws Exception {
		when(courseService.findById(anyInt())).thenReturn(Optional.of(courseDto));
		mockMvc.perform(get("/viewCourse/1").param("courseId", "1"))
				.andExpect(view().name("ViewCourse")).andExpect(status().isOk());
	}
	
	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testViewCourseForInvalidCourse() throws Exception {
		when(courseService.findById(anyInt())).thenThrow(CourseNotFoundException.class);
		mockMvc.perform(get("/viewCourse/1").param("courseId", "1"))
				.andExpect(status().is3xxRedirection());
		assertThrows(CourseNotFoundException.class,()->{
			courseService.findById(anyInt());
		});
	}

	@WithMockUser(username = "ShivaTeja",roles={"ADMIN"})
	@Test
	void testDeleteCourse() throws Exception {
		
		mockMvc.perform(get("/deleteCourse/1")).andExpect(status().is3xxRedirection());
		verify(courseService, times(1)).delete(anyInt());
		
	}

	@WithMockUser(username = "ShivaTeja",roles={"ADMIN"})
	@Test
	void testCreateCourse() throws Exception {
		mockMvc.perform(get("/createCourse")).andExpect(view().name("CreateCourse"))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "ShivaTeja",roles={"ADMIN"})
	@Test
	void testCreateCourseCourseDtoBindingResultHttpSession() throws Exception {

		mockMvc.perform(post("/createCourse").param("courseId", "1").param("courseName", courseDto.getCourseName())
				.param("syllabus", courseDto.getSyllabus())).andExpect(status().is3xxRedirection());
		verify(courseService, times(1)).save(any());
	}

	@WithMockUser(username = "ShivaTeja",roles={"ADMIN"})
	@Test
	void testUpdateCourseIntHttpSession() throws Exception {
		when(courseService.findById(anyInt())).thenReturn(Optional.of(courseDto));
		mockMvc.perform(get("/updateCourse/1").param("courseId", "1"))
				.andExpect(view().name("UpdateCourse")).andExpect(status().isOk());
	}

	@WithMockUser(username = "ShivaTeja",roles={"ADMIN"})
	@Test
	void testUpdateCourseCourseDtoBindingResultHttpSession() throws Exception {

		mockMvc.perform(post("/updateCourse").param("courseId", "1").param("courseName", courseDto.getCourseName())
				.param("syllabus", courseDto.getSyllabus())).andExpect(status().is3xxRedirection());
		verify(courseService, times(1)).update(any());
	}

}
