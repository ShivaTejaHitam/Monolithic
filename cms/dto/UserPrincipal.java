package com.epam.cms.dto;

import java.util.Collection;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.epam.cms.entities.AuthGroup;
import com.epam.cms.entities.Instructor;

public class UserPrincipal implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5015147089963217438L;
	
	private Instructor instructor;
	
	private List<AuthGroup> authGroup;

	public UserPrincipal(Instructor instructor,List<AuthGroup> authGroup) {
		super();
		this.instructor = instructor;
		this.authGroup = authGroup;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		if(null == authGroup) {
			return Collections.emptySet();
		}
		
		Set<SimpleGrantedAuthority> grantedAuthorities = new HashSet<>();
		
		authGroup.forEach(group -> {
			grantedAuthorities.add(new SimpleGrantedAuthority(group.getAuthGroup()));
		});
		
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return instructor.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return instructor.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
