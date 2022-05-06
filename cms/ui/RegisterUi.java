package com.epam.cms.ui;

import java.util.NoSuchElementException;
import java.util.Optional;

import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.cms.dto.InstructorDto;
import com.epam.cms.exceptions.AuthenticationFailedException;
import com.epam.cms.services.*;

@Component
public class RegisterUi implements AuthUi {

	private String username;
	private String password;
	private static final Logger LOGGER = LogManager.getLogger(RegisterUi.class);
	@Autowired
	private InstructorService instructorService;


	@Override
	public Optional<InstructorDto> authenticate() {
		Scanner scanner = new Scanner(System.in);

		Optional<InstructorDto> registered = Optional.empty();

		while (registered.isEmpty() || username.equals("") || password.equals("")) {

			try {
				LOGGER.info("Enter Your User Name");
				username = scanner.nextLine();

				LOGGER.info("Enter your Password");
				password = scanner.nextLine();
			} catch (NoSuchElementException exception) {
				LOGGER.error("Invalid Input");
				continue;
			}
			InstructorDto instructorDto = new InstructorDto();
			instructorDto.setUsername(username);
			instructorDto.setPassword(password);
			try {
				registered =  instructorService.save(instructorDto);
				LOGGER.debug("User Registered Successfully");
			}
			catch(AuthenticationFailedException exception) {
				LOGGER.debug(exception);
			}
			

		}
		return registered;
	}
}
