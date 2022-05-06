package com.epam.cms;

import java.util.Optional;

import java.util.Scanner;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.epam.cms.dto.InstructorDto;
//import com.epam.cms.ui.AuthUiFactory;
import com.epam.cms.ui.Menu;


//import com.epam.cms.ui.InstructorUi;


@Component
public class App {
	
	private static final Logger LOGGER = LogManager.getLogger(App.class);
	
	//@Autowired
	//private  AuthUiFactory authUiFactory;
     
    public void main(ApplicationContext context){
    	
    	
    	Scanner scanner = new Scanner(System.in);
    	
        LOGGER.info( "****	Welcome to EPAM CMS		****");
        
        int authUiId = -1;
		
        do{
        	
            new Menu().showMenu(new String[] {"Login","Register"});
            
            try {
            	authUiId = Integer.parseInt(scanner.nextLine());
            }
            catch(NumberFormatException exception) {
            	
            	LOGGER.error("Invalid Input");
            }
            
             
        }while(authUiId != 1 && authUiId != 2);

        //Optional<InstructorDto> userName = authUiFactory.getAuthUi(authUiId).authenticate();
        
        //context.getBean(InstructorUi.class).display(userName.get().getUsername(),context);
        
    }
}











