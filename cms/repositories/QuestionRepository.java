package com.epam.cms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.cms.entities.Question;


public interface QuestionRepository extends JpaRepository<Question,Integer>{
	
}
