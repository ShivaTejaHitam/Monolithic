package com.epam.cms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.cms.dto.InstructorDto;
import com.epam.cms.entities.Instructor;
import com.epam.cms.exceptions.InstructorNotFoundException;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.AuthGroupRepository;
import com.epam.cms.repositories.InstructorRepository;


@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {
	
	@Mock
	private InstructorRepository instructorRepository;
	@Autowired
	@InjectMocks
	private InstructorService instructorService;
	private InstructorDto instructorDto;
	
	@Mock
	private AuthGroupRepository authGroupRepository;
	
	@Captor
	private ArgumentCaptor<Instructor> argumentCaptorForInstructor;
	
	@BeforeEach
	void setUp() throws Exception{
		instructorDto = new InstructorDto();
		instructorDto.setUsername("Shiva");
		instructorDto.setPassword("Shiva@123");
	}
	
	
	@Test
	void testAuthenticateFailedForNoInstructor() {
		
		given(instructorRepository.findByUsername(anyString())).willThrow(InstructorNotFoundException.class);
		assertThrows(InstructorNotFoundException.class,()->{
			instructorService.save(instructorDto);
		});
	}
	
	@Test
	void testAuthenticateFailedForNoInvalidPassword() {
		
		given(instructorRepository.findByUsername(anyString())).willReturn(Mapper.convertDtoToEntity(instructorDto));
		InstructorDto instructorDto2 = new InstructorDto();
		instructorDto2.setUsername("Shiva");
		instructorDto2.setPassword("wrongPassword");
		assertThrows(InstructorNotFoundException.class,()->{
			instructorService.save(instructorDto2);
		});
	}
	
	@Test
	void testAuthenticate(){
		given(instructorRepository.findByUsername(anyString())).willReturn(Mapper.convertDtoToEntity(instructorDto));
		assertEquals(instructorDto.getUsername(),instructorService.findByUsername(instructorDto.getUsername()).getUsername());
		verify(instructorRepository,times(1)).findByUsername(anyString());
	}
	
	@Test
	void testAuthenticateForRegister() {
		 given(instructorRepository.save(any())).willReturn(Mapper.convertDtoToEntity(instructorDto));
		 instructorService.save(instructorDto);
		 verify(instructorRepository,times(1)).save(argumentCaptorForInstructor.capture());
		 assertEquals("Shiva",argumentCaptorForInstructor.getValue().getUsername());
	}
	
	@Test
	void testAuthenticateFailedForRegister() {
		
		instructorDto.setUsername("Unknown");
		given(instructorRepository.findByUsername("Unknown")).willReturn(Mapper.convertDtoToEntity(instructorDto));
		assertThrows(InstructorNotFoundException.class,()->{
			instructorService.save(instructorDto);
		});
	}

}
