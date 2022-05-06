package com.epam.cms.services;

import com.epam.cms.dto.CourseDto;


import com.epam.cms.entities.Course;
import com.epam.cms.exceptions.CourseNotFoundException;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.CourseRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

	@Autowired
	private CourseRepository courseRepository;

	public CourseDto save(CourseDto courseDto) {
		return Mapper.convertEntityToDto(courseRepository.save(Mapper.convertDtoToEntity(courseDto)));
	}

	public List<CourseDto> findAll() {
		return Mapper.convertListOfCourseEntityToDto(courseRepository.findAll());
	}

	public Optional<CourseDto> findById(int courseId) {
		Optional<Course> courseOptional = courseRepository.findById(courseId);
		courseOptional.orElseThrow(() -> new CourseNotFoundException("Course with " + courseId + " not found"));
		return Optional.of(Mapper.convertEntityToDto(courseOptional.get()));
	}

	public void delete(int courseId) {
		courseRepository.deleteById(courseId);
	}
	
	public CourseDto update(CourseDto courseDto) {
		return Mapper.convertEntityToDto(courseRepository.save(Mapper.convertDtoToEntity(courseDto)));
	}

}
