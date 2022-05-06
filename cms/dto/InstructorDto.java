package com.epam.cms.dto;

import javax.validation.constraints.NotBlank;



public class InstructorDto{
	
	private int instructorId;
	@NotBlank(message = "Please Enter  the username")
	private String username;
	@NotBlank(message = "Please Enter  the password")
	private String password;
	private boolean enabled=true;	
	
	public int getInstructorId() {
		return instructorId;
	}

	public void setInstructorId(int instructorId) {
		this.instructorId = instructorId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
	public String toString() {
		return "InstructorDto [username=" + username + "]";
	}
	
	
	
}
