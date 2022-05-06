package com.epam.cms.mapper;

import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.dto.QuestionDto;
import com.epam.cms.entities.Assignment;
import com.epam.cms.entities.Course;
import com.epam.cms.entities.Instructor;
import com.epam.cms.entities.Question;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Mapper {

	private static ModelMapper mapper = new ModelMapper();
	
	public static Instructor convertDtoToEntity(InstructorDto instructorDto) {

		return mapper.map(instructorDto, Instructor.class);
	}

	public static InstructorDto convertEntityToDto(Instructor instructor) {

		return mapper.map(instructor, InstructorDto.class);
	}

	public static Course convertDtoToEntity(CourseDto courseDto) {

		return mapper.map(courseDto, Course.class);
	}
	
	public static CourseDto convertEntityToDto(Course course) {

		return mapper.map(course, CourseDto.class);
	}
	
	
	public static Assignment convertDtoToEntity(AssignmentDto assignmentDto) {

		return mapper.map(assignmentDto, Assignment.class);
	}
	
	public static AssignmentDto convertEntityToDto(Assignment assignment) {

		return mapper.map(assignment, AssignmentDto.class);
	}
	
	public static List<Assignment> convertListOfAssignmentDtoToEntity(List<AssignmentDto> assignmentDtos){
		return assignmentDtos.stream().map(a -> mapper.map(a,Assignment.class)).collect(Collectors.toList());
	}
	
	public static List<AssignmentDto> convertListOfAssignmentEntityToDto(List<Assignment> assignments){
		
		return assignments.stream().map(a -> mapper.map(a,AssignmentDto.class)).collect(Collectors.toList());
	}
	
	public static List<Course> convertListOfCourseDtoToEntity(List<CourseDto> courseDtos){
		
		return courseDtos.stream().map(c -> mapper.map(c,Course.class)).collect(Collectors.toList());
	}
	
	public static List<CourseDto> convertListOfCourseEntityToDto(List<Course> courses){
		
		return courses.stream().map(a -> mapper.map(a,CourseDto.class)).collect(Collectors.toList());
	}
	
	public static List<Question> convertListOfQuestionDtoToEntity(List<QuestionDto> questionDtos){
		return questionDtos.stream().map(q -> mapper.map(q,Question.class)).collect(Collectors.toList());
	}
	
	public static List<QuestionDto> convertListOfQuestionEntityToDto(List<Question> questions){
		
		return questions.stream().map(q -> mapper.map(q,QuestionDto.class)).collect(Collectors.toList());
	}

	public static Question convertDtoToEntity(QuestionDto questionDto) {
		return mapper.map(questionDto, Question.class);
	}

	public static QuestionDto convertEntityToDto(Question question) {
		return mapper.map(question, QuestionDto.class);
	}
	
	public static String mapToJson(Object obj) throws JsonProcessingException {
	      ObjectMapper objectMapper = new ObjectMapper();
	      objectMapper.registerModule(new JavaTimeModule());
	      return objectMapper.writeValueAsString(obj);
	}
	
	public static <T> T mapFromJson(String json, Class<T> className)
	      throws JsonParseException, JsonMappingException, IOException {
	      
	      ObjectMapper objectMapper = new ObjectMapper();
	      objectMapper.registerModule(new JavaTimeModule());
	      return objectMapper.readValue(json, className); 
	}
	
}
