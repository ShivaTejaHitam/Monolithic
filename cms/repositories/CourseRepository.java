package com.epam.cms.repositories;


import org.springframework.data.jpa.repository.JpaRepository;


import com.epam.cms.entities.Course;


public interface CourseRepository extends JpaRepository<Course,Integer>{
	
	
}
