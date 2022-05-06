package com.epam.cms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.epam.cms.services.JwtUtil;

@Component
public class CurrentUser {
	
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	@Autowired
	static JwtUtil jwtUtil;
	
	public  String getUsername() {
		return jwtUtil.extractUsername(authentication.getName());
	}
	
	public  String getUser() {
		return authentication.getName();
	}
}
