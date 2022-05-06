package com.epam.cms.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class AssignmentOperationFactory {
	private static final Logger LOGGER = LogManager.getLogger(AssignmentOperationFactory.class);

	public void callAssignmentOperation(int assignmentOperationId,String userName,ApplicationContext context) {
		
		AssignmentUi assignmentUi = context.getBean(AssignmentUi.class);
		
		if(assignmentOperationId == 1) {
			assignmentUi.createAssignment(userName,context);
		}
		else if(assignmentOperationId == 2) {
			assignmentUi.showAllAssignments(userName,0,context);
		}
		else if(assignmentOperationId == 3) {
			assignmentUi.getAssignmentById(userName,0,context);
		}
		else if(assignmentOperationId == 4) {
			assignmentUi.updateAssignment(userName,context);
		}
		
		else if(assignmentOperationId == 5) {
			assignmentUi.deleteAssignment(userName,context);
		}
		
		else if(assignmentOperationId == 6) {
			context.getBean(InstructorUi.class).display(userName,context);
		}
		
		else {
			LOGGER.error("Invalid Option. Please select the appropriate option");
		}
		
		
	}
}
