package com.epam.cms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.entities.Course;
import com.epam.cms.entities.Instructor;
import com.epam.cms.exceptions.CourseNotFoundException;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.CourseRepository;
import com.epam.cms.repositories.InstructorRepository;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

	@Mock
	private CourseRepository courseRepository;
	
	@Autowired
	@InjectMocks
	private CourseService courseService;
	private InstructorDto instructorDto;
	private CourseDto courseDto;
	@Captor
	private ArgumentCaptor<Course> argumentCaptorForCourse;

	@BeforeEach
	void setUp() throws Exception {
		//courseService = new CourseService(courseRepository);
		instructorDto = new InstructorDto();
		courseDto = new CourseDto();

		instructorDto.setUsername("Shiva");
		instructorDto.setPassword("Shiva@123");

		courseDto.setCourseId(1);
		courseDto.setCourseName("Design Patterns");
		courseDto.setSyllabus("5 Units");
		courseDto.setInstructor(instructorDto);

	}

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	void testSave() {
		given(courseRepository.save(any())).willReturn(Mapper.convertDtoToEntity(courseDto));
		courseService.save(courseDto);
		verify(courseRepository, times(1)).save(argumentCaptorForCourse.capture());
		assertEquals("Design Patterns", argumentCaptorForCourse.getValue().getCourseName());

	}

	@Test
	void testGetAll() {

		List<CourseDto> mockCourses = Arrays.asList(courseDto);
		given(courseRepository.findAll()).willReturn(Mapper.convertListOfCourseDtoToEntity(mockCourses));
		List<CourseDto> courses = courseService.findAll();
		assertEquals(1, courses.size());
		verify(courseRepository, times(1)).findAll();
		
	}

	@Test
	void testGetAllForZeroCourses() {

		List<CourseDto> mockCourses = new ArrayList<>();
		given(courseRepository.findAll()).willReturn(Mapper.convertListOfCourseDtoToEntity(mockCourses));
		List<CourseDto> courses = courseService.findAll();
		assertEquals(0, courses.size());
		verify(courseRepository, times(1)).findAll();

	}

	@Test
	void testGet() {

		given(courseRepository.findById(anyInt())).willReturn(Optional.of(Mapper.convertDtoToEntity(courseDto)));
		assertEquals(courseDto.getClass(),
				courseService.findById(2).get().getClass());
		verify(courseRepository, times(1)).findById(anyInt());

	}

	@Test
	void testGetForNoCourse() {

		given(courseRepository.findById(anyInt())).willReturn(Optional.empty());
		assertThrows(CourseNotFoundException.class, () -> {
			courseService.findById(courseDto.getCourseId());
		});

		verify(courseRepository, times(1)).findById(anyInt());

	}

	@Test
	void testDelete() {

		courseService.delete(courseDto.getCourseId());
		verify(courseRepository, times(1)).deleteById(anyInt());
	}

	@Test
	void testUpdate() {
		given(courseRepository.save(any())).willReturn(Mapper.convertDtoToEntity(courseDto));
		courseService.update(courseDto);
		verify(courseRepository, times(1)).save(argumentCaptorForCourse.capture());
		assertEquals("Design Patterns", argumentCaptorForCourse.getValue().getCourseName());
	}
	
	
}
