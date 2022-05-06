package com.epam.cms.ui;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.epam.cms.dto.InstructorDto;
import com.epam.cms.exceptions.AuthenticationFailedException;
import com.epam.cms.exceptions.InstructorNotFoundException;
import com.epam.cms.services.InstructorService;

@Component
public class LoginUi implements AuthUi {

	private String username;
	private String password;
	private static final Logger LOGGER = LogManager.getLogger(LoginUi.class);
	@Autowired
	private InstructorService instructorService;
	
	@Override
	public Optional<InstructorDto> authenticate() {

		Scanner scanner = new Scanner(System.in);
		
		Optional<InstructorDto> loggedIn = checkLoginStatus(scanner, Optional.empty());
		
		return loggedIn;
	}

	private Optional<InstructorDto> checkLoginStatus(Scanner scanner, Optional<InstructorDto> loggedIn) {
		while (loggedIn.isEmpty()) {
			try {

				LOGGER.info("Enter your UserName");
				username = scanner.nextLine();

				LOGGER.info("Enter your password");
				password = scanner.nextLine();
			} catch (Exception exception) {
				LOGGER.error("Invalid Input");
				continue;
			}
			InstructorDto instructorDto = new InstructorDto();
			instructorDto.setUsername(username);
			instructorDto.setPassword(password);
			
			try {
				loggedIn = Optional.of(instructorService.findByUsername(instructorDto.getUsername()));)
			}
			catch(InstructorNotFoundException exception) {
				LOGGER.info("Invalid UserName or Password");
			}
		}
		return loggedIn;
	}
	
}

