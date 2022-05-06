package com.epam.cms.controllers;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cms.config.CurrentUser;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.services.AppUserDetailsService;
import com.epam.cms.services.CourseService;
import com.epam.cms.services.InstructorService;
import com.epam.cms.services.JwtUtil;

//@WebMvcTest(CourseRestController.class)
@SpringBootTest
@AutoConfigureMockMvc
class CourseRestControllerTest {
	
	
	@Autowired
	private MockMvc mockMvc;

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
	
	
	InstructorDto instructorDto;
	CourseDto courseDto;

	@BeforeEach
	void setUp() throws Exception {
		
		instructorDto = new InstructorDto();
		courseDto = new CourseDto();
		
		instructorDto.setUsername("ShivaTeja");
		instructorDto.setPassword("password");

		courseDto.setCourseId(2);
		courseDto.setCourseName("Design Patterns");
		courseDto.setSyllabus("5 units");
		courseDto.setInstructor(instructorDto);
		
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@WithMockUser(username = "ShivaTeja",password= "password",roles={"USER","ADMIN"})
	@Test
	void testCoursesList() throws Exception{
		List<CourseDto> courses = new ArrayList<>();
		courses.add(courseDto);
		when(courseService.findAll()).thenReturn(courses);
		when(currentUser.getUsername()).thenReturn("ShivaTeja");

		mockMvc.perform(get("/courses").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(1)));
	}

	@WithMockUser(username = "ShivaTeja",password= "password",roles={"USER","ADMIN"})
	@Test
	void testCreateCourse() throws Exception{
		when(courseService.save(any())).thenReturn(courseDto);
		when(currentUser.getUsername()).thenReturn("ShivaTeja");

		mockMvc.perform(
				post("/courses").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(courseDto)))
				.andExpect(status().isCreated());
	}

	@WithMockUser(username = "ShivaTeja",password= "password",roles={"USER","ADMIN"})
	@Test
	void testUpdateCourse() throws Exception{
		when(courseService.findById(anyInt())).thenReturn(Optional.of(courseDto));
		when(courseService.update(any())).thenReturn(courseDto);
		when(instructorService.findByUsername(anyString())).thenReturn(instructorDto);
		when(currentUser.getUsername()).thenReturn("ShivaTeja");

		mockMvc.perform(
				put("/courses").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(courseDto)))
				.andExpect(status().isOk());
	}

	@WithMockUser(username = "ShivaTeja",password= "password",roles={"USER","ADMIN"})
	@Test
	void testViewCourse() throws Exception{
		when(courseService.findById(anyInt())).thenReturn(Optional.of(courseDto));
		when(currentUser.getUsername()).thenReturn("ShivaTeja");
		mockMvc.perform(get("/courses/1").accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
				.andExpect(jsonPath("$.*").exists());
	}

	@WithMockUser(username = "ShivaTeja",password= "password",roles={"USER","ADMIN"})
	@Test
	void testDeleteCourse() throws Exception{
		when(courseService.findById(anyInt())).thenReturn(Optional.of(courseDto));
		when(currentUser.getUsername()).thenReturn("ShivaTeja");
		mockMvc.perform(delete("/courses/1")).andExpect(status().isNoContent()).andReturn().getResponse()
		.getContentAsString().equals("course Deleted successfully");
	}

}
