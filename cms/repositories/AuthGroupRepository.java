package com.epam.cms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.cms.entities.AuthGroup;

public interface AuthGroupRepository extends JpaRepository<AuthGroup,Long>{
	
	List<AuthGroup> findByUsername(String username);
}
