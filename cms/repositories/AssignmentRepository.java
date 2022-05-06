package com.epam.cms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.cms.entities.Assignment;


public interface AssignmentRepository extends JpaRepository<Assignment,Integer>{
	
}
