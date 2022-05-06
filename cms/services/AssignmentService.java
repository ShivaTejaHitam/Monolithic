package com.epam.cms.services;

import com.epam.cms.dto.AssignmentDto;
import com.epam.cms.dto.CourseDto;
import com.epam.cms.entities.Assignment;

import com.epam.cms.exceptions.AssignmentNotFoundException;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.AssignmentRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {

	@Autowired
	private AssignmentRepository assignmentRepository;

	public AssignmentDto save(AssignmentDto assignmentDto) {
		assignmentDto.setMaxMarks(
				assignmentDto.getQuestions().stream().map(q -> q.getMaxMarks()).reduce(0, (a, b) -> (a + b)));
		return Mapper.convertEntityToDto(assignmentRepository.save(Mapper.convertDtoToEntity(assignmentDto)));
	}
	
	public List<AssignmentDto> findAll() {
		return Mapper.convertListOfAssignmentEntityToDto(assignmentRepository.findAll());
	}

	public Optional<AssignmentDto> findById(int assignmentId) {
		Optional<Assignment> assignmentOptional = assignmentRepository.findById(assignmentId);
		assignmentOptional.orElseThrow(() -> new AssignmentNotFoundException("Assignment with " + assignmentId + " not found"));
		return Optional.of(Mapper.convertEntityToDto(assignmentOptional.get()));
	}

	public void delete(int assignmentId) {
		
		assignmentRepository.deleteById(assignmentId);
	}
	
	public AssignmentDto update(AssignmentDto assignmentDto) {
		assignmentDto.setMaxMarks(
				assignmentDto.getQuestions().stream().map(q -> q.getMaxMarks()).reduce(0, (a, b) -> (a + b)));
		return Mapper.convertEntityToDto(assignmentRepository.save(Mapper.convertDtoToEntity(assignmentDto)));
	}
	
}
