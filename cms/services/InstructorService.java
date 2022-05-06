package com.epam.cms.services;
import java.util.Optional;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.epam.cms.dto.CourseDto;
import com.epam.cms.dto.InstructorDto;
import com.epam.cms.entities.AuthGroup;
import com.epam.cms.entities.Course;
import com.epam.cms.entities.Instructor;
import com.epam.cms.exceptions.AuthenticationFailedException;
import com.epam.cms.exceptions.CourseNotFoundException;
import com.epam.cms.exceptions.InstructorNotFoundException;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.AuthGroupRepository;
import com.epam.cms.repositories.InstructorRepository;

@Service
public class InstructorService {
	
	
	@Autowired
	private InstructorRepository instructorRepository;
	
	@Autowired
	private AuthGroupRepository authGroupRepository;

	/*
	public Optional<InstructorDto> findById(InstructorDto instructorDto) {
		
		Optional<Instructor> instructorOptional = Optional.of(instructorRepository.findByUsername(instructorDto.getUsername()));
	
	    if(instructorOptional.isEmpty() || !instructorOptional.get().getPassword().equals(instructorDto.getPassword()))
			throw new AuthenticationFailedException("Invalid Username or Password");
		
		return Optional.of(instructorDto);
		
	}*/
	
	public InstructorDto findByUsername(String username) {
		Instructor instructor = instructorRepository.findByUsername(username);		
		if(instructor == null) {
			throw new InstructorNotFoundException("User Doesn't exist");
		}
		return Mapper.convertEntityToDto(instructor);
	}
	
	public Optional<InstructorDto> save(InstructorDto instructorDto) {
		
		Optional<InstructorDto> message = Optional.empty();
	
		if(instructorRepository.findByUsername(instructorDto.getUsername())!=null) {
			throw new InstructorNotFoundException("User Already Exists");
		}
		
		PasswordEncoder encoder = new BCryptPasswordEncoder(11);
		instructorDto.setPassword(encoder.encode(instructorDto.getPassword()));
		AuthGroup authGroup = new AuthGroup();
		authGroup.setUsername(instructorDto.getUsername());
		authGroup.setAuthGroup("ADMIN");
		message = Optional.of(Mapper.convertEntityToDto(instructorRepository.save(Mapper.convertDtoToEntity(instructorDto))));
		authGroupRepository.save(authGroup);
		return message;
		
	}
	
}




