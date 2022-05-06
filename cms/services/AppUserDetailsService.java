package com.epam.cms.services;

import java.util.Collection;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.epam.cms.dto.UserPrincipal;
import com.epam.cms.entities.AuthGroup;
import com.epam.cms.entities.Instructor;
import com.epam.cms.repositories.AuthGroupRepository;
import com.epam.cms.repositories.InstructorRepository;


@Service
public class AppUserDetailsService implements UserDetailsService{

	private final InstructorRepository repository;
	private final AuthGroupRepository authGroupRepository;
	
	public AppUserDetailsService(InstructorRepository repository,AuthGroupRepository authGroupRepository) {
		super();
		this.repository = repository;
		this.authGroupRepository = authGroupRepository;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Instructor instructor = repository.findByUsername(username);
		if (null == instructor) {
			throw new UsernameNotFoundException("Cannot find username :" + username);
		}	
		
		List<AuthGroup> authorities = authGroupRepository.findByUsername(username);
		
		return new UserPrincipal(instructor,authorities);
	}

}
