package com.epam.cms;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.epam.cms.entities.AuthGroup;
import com.epam.cms.entities.Instructor;
import com.epam.cms.repositories.AuthGroupRepository;
import com.epam.cms.repositories.InstructorRepository;


@SpringBootApplication
public class OnlinecmsApplication implements CommandLineRunner{

	
	
	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(OnlinecmsApplication.class, args);
		context.getBean(App.class).main(context);
		((AbstractApplicationContext) context).close();
	}
	
	@Override
	public void run(String... args) throws Exception {
		
	}
}
