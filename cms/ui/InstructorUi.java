package com.epam.cms.ui;
import java.util.*;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class InstructorUi implements UserUi{
	
	private static final Logger LOGGER = LogManager.getLogger(InstructorUi.class);
	

	public void display(String userName,ApplicationContext context)
	{
		Scanner scanner = new Scanner(System.in);
		
		int instructorOptionId = -1;
		
		do {
		
			new Menu().showMenu(new String[] {"Courses","Assignments","LogOut"});
			
			try {
				
				instructorOptionId = Integer.parseInt(scanner.nextLine());

			}
			catch(NumberFormatException exception) {
				LOGGER.error("Invalid Option ");
			}
			catch(NoSuchElementException exception) {
				LOGGER.error("Please enter any Input");
			}
			
		}while(instructorOptionId != 1 && instructorOptionId != 2 && instructorOptionId != 3);
		
		
		InstructorOptionFactory instructorOptionFactory = new InstructorOptionFactory();
		instructorOptionFactory.getInstructorOption(instructorOptionId,context).display(userName,context);
		
	}
}
