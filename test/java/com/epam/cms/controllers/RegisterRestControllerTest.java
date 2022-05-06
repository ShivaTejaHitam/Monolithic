package com.epam.cms.controllers;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.cms.dto.InstructorDto;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.services.AppUserDetailsService;
import com.epam.cms.services.InstructorService;
import com.epam.cms.services.JwtUtil;


@WebMvcTest(RegisterRestController.class)
class RegisterRestControllerTest {
	
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
	
	

	@Test
	void testAuthenticate() throws Exception{
		
		when(instructorService.findByUsername(anyString())).thenReturn(instructorDto);
		mockMvc.perform(
				post("/register").contentType(MediaType.APPLICATION_JSON_VALUE).content(Mapper.mapToJson(instructorDto)))
				.andExpect(status().isCreated());
	}

}
