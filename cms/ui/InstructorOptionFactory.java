package com.epam.cms.ui;

import org.springframework.context.ApplicationContext;

public class InstructorOptionFactory {
	
	public InstructorOption getInstructorOption(int instructorOptionId,ApplicationContext context){
		
		InstructorOption instructorOption = null;
		
		if(instructorOptionId == 1){
			instructorOption =  context.getBean(CourseUi.class);
		}
		else if(instructorOptionId == 2) {
			instructorOption = context.getBean(AssignmentUi.class);
		}
		else if(instructorOptionId == 3) {
			context.getBean(LoginUi.class).authenticate();
		}
		else {
			System.out.println("Please Select Valid Option");
		}
		
		return instructorOption;
	}
}
