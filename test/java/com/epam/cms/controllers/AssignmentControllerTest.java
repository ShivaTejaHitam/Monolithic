package com.epam.cms.controllers;

import static org.mockito.ArgumentMatchers.any;





import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cms.config.CurrentUser;
import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.dto.QuestionDto;

import com.epam.cms.exceptions.AssignmentNotFoundException;
import com.epam.cms.services.AppUserDetailsService;
import com.epam.cms.services.AssignmentService;
import com.epam.cms.services.CourseService;
import com.epam.cms.services.InstructorService;
import com.epam.cms.services.JwtUtil;


@WebMvcTest(AssignmentController.class)
class AssignmentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AssignmentService assignmentService;
	
	@MockBean
	private CourseService courseService;
	
	@MockBean
	private InstructorService instructorService;
	
	@MockBean
	private AppUserDetailsService appUserDetailsService;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	@MockBean
	private CurrentUser currentUser;
	
	
	InstructorDto instructor;
	AssignmentDto assignmentDto;
	CourseDto course;
	List<QuestionDto> questions;
	QuestionDto question;
	
	
	
	
	@BeforeEach
	void setUp() throws Exception {
		instructor = new InstructorDto();
		course = new CourseDto();
		assignmentDto = new AssignmentDto();
		questions = new ArrayList<>();
		question = new QuestionDto();
		
		instructor.setUsername("Shiva Teja");
		instructor.setPassword("Shiva@123");

		course.setCourseId(2);
		course.setCourseName("Design Patterns");
		course.setSyllabus("5 units");
		course.setInstructor(instructor);
		
		assignmentDto.setAssignmentId(1);
		assignmentDto.setCourse(course);
		assignmentDto.setDeadLine(LocalDate.now().plusDays(7));
		assignmentDto.setInstructor(instructor);
		assignmentDto.setTitle("Test Assignment");
		
		question.setQuestionId(1);
		question.setMaxMarks(5);
		question.setDescription("Write a");
		
		questions.add(question);
		
		assignmentDto.setQuestions(questions);
		
		
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testCreateAssignment() throws Exception{
		mockMvc.perform(get("/createAssignment/1")).andExpect(view().name("CreateAssignment"))
		.andExpect(status().isOk());
	}

	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testCreateAssignmentForValidData() throws Exception {
		given(courseService.findById(anyInt())).willReturn(Optional.of(course));
		mockMvc.perform(post("/createAssignment/1")
				.param("courseId", "courseId").requestAttr("assignmentDto", assignmentDto)).andExpect(status().is3xxRedirection());
		verify(assignmentService, times(1)).save(any());
	}
	
	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testCreateAssignmentForInvalidData() throws Exception {
		
		mockMvc.perform(post("/createAssignment/1")
				).andExpect(status().isOk());
		verify(assignmentService, times(0)).save(any());
	}
	
	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testViewAssignment() throws Exception{
		when(assignmentService.findById(anyInt())).thenReturn(Optional.of(assignmentDto));
		mockMvc.perform(get("/viewAssignment/1")).andExpect(view().name("ViewAssignment")).andExpect(status().isOk());
	}
	
	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testViewAssignmentForInvalidCourseId() throws Exception{
		when(assignmentService.findById(anyInt())).thenThrow(AssignmentNotFoundException.class);
		mockMvc.perform(get("/viewAssignment/1")).andExpect(status().is3xxRedirection());
	}
	
	
	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testDeleteAssignment() throws Exception {
		
		when(assignmentService.findById(anyInt())).thenReturn(Optional.of(assignmentDto));
		mockMvc.perform(get("/deleteAssignment/1")).andExpect(status().is3xxRedirection());
		verify(assignmentService,times(1)).delete(any());
	}

	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testUpdateAssignmentIntHttpSession() throws Exception {
		when(assignmentService.findById(anyInt())).thenReturn(Optional.of(assignmentDto));
		mockMvc.perform(get("/updateAssignment/1").param("assignmentId", "1"))
				.andExpect(view().name("UpdateAssignment")).andExpect(status().isOk());
	}
	
	
	@WithMockUser(username = "ShivaTeja",roles={"USER","ADMIN"})
	@Test
	void testUpdateAssignmentAssignmentDtoBindingResultHttpSession() throws Exception{
		mockMvc.perform(post("/updateAssignment")
				).andExpect(status().isOk());
		verify(assignmentService, times(0)).save(any());
	}
	
}
