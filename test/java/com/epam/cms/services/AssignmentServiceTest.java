package com.epam.cms.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.dto.QuestionDto;
import com.epam.cms.entities.Assignment;
import com.epam.cms.exceptions.AssignmentNotFoundException;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.AssignmentRepository;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceTest {
	
	@Mock
	private AssignmentRepository assignmentRepository;
	@Autowired
	@InjectMocks
	private AssignmentService assignmentService;
	private InstructorDto instructor;
	private CourseDto course;
	private AssignmentDto assignment;
	private List<QuestionDto> questions;
	private QuestionDto question;
	@Captor
	private ArgumentCaptor<Assignment> argumentCaptorForAssignment;
	
	
	@BeforeEach
	void setUp() throws Exception{
		
		instructor = new InstructorDto();
		course = new CourseDto();
		assignment = new AssignmentDto();
		questions = new ArrayList<>();
		question = new QuestionDto();
		
		instructor.setUsername("Shiva Teja");
		instructor.setPassword("Shiva@123");

		course.setCourseId(2);
		course.setCourseName("Design Patterns");
		course.setSyllabus("5 units");
		course.setInstructor(instructor);
		
		assignment.setAssignmentId(1);
		assignment.setCourse(course);
		assignment.setDeadLine(LocalDate.now().plusDays(7));
		assignment.setInstructor(instructor);
		assignment.setTitle("Test Assignment");
		
		question.setQuestionId(1);
		question.setMaxMarks(5);
		question.setDescription("Write a");
		question.setAssignment(assignment);
		questions.add(question); 
		assignment.setQuestions(questions);
		
	}
	
	
	@AfterEach
	void tearDown() throws Exception{
		
	}
	
	
	@Test
	void testSave() {
		 given(assignmentRepository.save(any())).willReturn(Mapper.convertDtoToEntity(assignment));
		 assignmentService.save(assignment);
		 verify(assignmentRepository,times(1)).save(argumentCaptorForAssignment.capture());
		 assertEquals("Test Assignment",argumentCaptorForAssignment.getValue().getTitle());
	}
	
	
	@Test
	void testGetAll() {
		
		List<AssignmentDto> mockAssignments = Arrays.asList(assignment);
		given(assignmentRepository.findAll()).willReturn(Mapper.convertListOfAssignmentDtoToEntity(mockAssignments));
		List<AssignmentDto> assignments = assignmentService.findAll();
		assertEquals(1,assignments.size());
		verify(assignmentRepository,times(1)).findAll();

	}
	
	@Test
	void testGetAllForNoAssignment() {
		
		List<AssignmentDto> mockAssignments = new ArrayList<>();
		given(assignmentRepository.findAll()).willReturn(Mapper.convertListOfAssignmentDtoToEntity(mockAssignments));
		List<AssignmentDto> assignments = assignmentService.findAll();
		assertEquals(0,assignments.size());
		verify(assignmentRepository,times(1)).findAll();

		
	}

	@Test
	void testGet() {
		
		given(assignmentRepository.findById(anyInt())).willReturn(Optional.of(Mapper.convertDtoToEntity(assignment)));		
		assertEquals(AssignmentDto.class,assignmentService.findById(1).get().getClass());
		verify(assignmentRepository,times(1)).findById(anyInt());
	}
	
	@Test
	void testGetForNoAssignment() {
		
		given(assignmentRepository.findById(anyInt())).willReturn(Optional.empty());
		assertThrows(AssignmentNotFoundException.class, () -> {
			assignmentService.findById(10);
		}); 
		verify(assignmentRepository,times(1)).findById(anyInt());
	}
	
	
	@Test 
	void testDelete() { 
		 assignmentService.delete(assignment.getAssignmentId());
		 verify(assignmentRepository,times(1)).deleteById(anyInt());
	}
	
	@Test
	void testUpdate() {
		given(assignmentRepository.save(any())).willReturn(Mapper.convertDtoToEntity(assignment));
		 assignmentService.update(assignment);
		 verify(assignmentRepository,times(1)).save(argumentCaptorForAssignment.capture());
		 assertEquals("Test Assignment",argumentCaptorForAssignment.getValue().getTitle());
	}

}
