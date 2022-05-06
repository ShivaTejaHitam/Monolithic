package com.epam.cms.controllers;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cms.config.CurrentUser;
import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.dto.QuestionDto;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.services.AppUserDetailsService;
import com.epam.cms.services.AssignmentService;
import com.epam.cms.services.InstructorService;
import com.epam.cms.services.JwtUtil;



@WebMvcTest(AssignmentRestController.class)
class AssignmentRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
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

	InstructorDto instructorDto;
	AssignmentDto assignmentDto;
	CourseDto courseDto;
	List<QuestionDto> questions;
	QuestionDto questionDto;

	@BeforeEach
	void setUp() throws Exception {

		instructorDto = new InstructorDto();
		courseDto = new CourseDto();
		assignmentDto = new AssignmentDto();
		questions = new ArrayList<>();
		questionDto = new QuestionDto();

		instructorDto.setUsername("ShivaTeja");
		instructorDto.setPassword("password");

		courseDto.setCourseId(1);
		courseDto.setCourseName("Design Patterns");
		courseDto.setSyllabus("5 units");
		courseDto.setInstructor(instructorDto);

		assignmentDto.setAssignmentId(1);
		assignmentDto.setCourse(courseDto);
		assignmentDto.setDeadLine(LocalDate.now().plusDays(7));
		assignmentDto.setInstructor(instructorDto);
		assignmentDto.setTitle("Test Assignment");

		questionDto.setQuestionId(1);
		questionDto.setMaxMarks(5);
		questionDto.setDescription("Write an assignment");
		questions.add(questionDto);
		
		assignmentDto.setQuestions(questions);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@WithMockUser(username = "ShivaTeja")
	@Test
	void testAssignmentsList() throws Exception {

		List<AssignmentDto> assignments = new ArrayList<>();
		assignments.add(assignmentDto);
		when(assignmentService.findAll()).thenReturn(assignments);
		when(currentUser.getUsername()).thenReturn("ShivaTeja");
		mockMvc.perform(get("/courses/1/assignments").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)));
	}

	@WithMockUser(username = "ShivaTeja")
	@Test
	void testCreateAssignment() throws Exception {
		when(assignmentService.save(any())).thenReturn(assignmentDto);
		mockMvc.perform(
				post("/courses/1/assignments").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(assignmentDto)))
				.andExpect(status().isCreated());
	}

	@WithMockUser(username = "ShivaTeja")
	@Test
	void testUpdateAssignment() throws Exception{
		when(assignmentService.save(any())).thenReturn(assignmentDto);
		mockMvc.perform(
				put("/courses/1/assignments").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(assignmentDto)))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "ShivaTeja")
	@Test
	void testViewAssignment() throws Exception {
		when(assignmentService.findById(anyInt())).thenReturn(Optional.of(assignmentDto));
		mockMvc.perform(get("/courses/1/assignments/1").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").exists());
	}

	@WithMockUser(username = "ShivaTeja")
	@Test
	void testDeleteAssignment() throws Exception {
		mockMvc.perform(delete("/courses/1/assignments/1")).andExpect(status().isNoContent()).andReturn().getResponse()
				.getContentAsString().equals("assignment Deleted successfully");
	}
	
}
