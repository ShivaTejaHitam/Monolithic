package com.epam.cms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.cms.dto.AuthenticationRequest;
import com.epam.cms.dto.AuthenticationResponse;
import com.epam.cms.services.AppUserDetailsService;
import com.epam.cms.services.JwtUtil;

@RestController
public class AuthApiController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private AppUserDetailsService userDetailsService;
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken (@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException bce) {
			throw new Exception("Invalid credentials");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return  ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
}
