package com.epam.cms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.epam.cms.entities.Instructor;


public interface InstructorRepository extends JpaRepository<Instructor,Long>{

	Instructor findByUsername(String username);
	
}
