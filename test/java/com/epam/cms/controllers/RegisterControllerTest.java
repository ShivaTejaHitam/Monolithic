package com.epam.cms.controllers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cms.dto.InstructorDto;
import com.epam.cms.services.AppUserDetailsService;
import com.epam.cms.services.InstructorService;
import com.epam.cms.services.JwtUtil;

@WebMvcTest(RegisterController.class)
class RegisterControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private InstructorService instructorService;
	
	@MockBean
	private AppUserDetailsService appUserDetailsService;
	
	@MockBean
	private JwtUtil jwtUtil;
	
	InstructorDto instructorDto;
	
	
	@BeforeEach
	void setUp() throws Exception {
		instructorDto = new InstructorDto();
		instructorDto.setUsername("Shiva");
		instructorDto.setPassword("Shiva@123");
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	
	@Test
	void testRegister() throws Exception {
		mockMvc.perform(get("/registers")).andExpect(view().name("Register")).andExpect(status().isOk());
	}

	@Test
	void testRegisterInstructorDtoBindingResult() throws Exception {
		when(instructorService.findByUsername(anyString())).thenReturn(instructorDto);
		mockMvc.perform(post("/registers").param("username", instructorDto.getUsername()).param("password", instructorDto.getPassword())).andExpect(view().name("Login")).andExpect(status().isOk());
	}
	
}
