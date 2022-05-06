package com.epam.cms.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;

public class CourseOperationFactory {
	private static final Logger LOGGER = LogManager.getLogger(CourseOperationFactory.class);

	public void callCourseOperation(int courseOperationId,String userName,ApplicationContext context) {
		
		CourseUi courseUi = context.getBean(CourseUi.class);
		
		if(courseOperationId == 1) {
			courseUi.createCourse(userName,context);
		}
		else if(courseOperationId == 2) {
			courseUi.showAllCourses(userName,0,context);
		}
		else if(courseOperationId == 3) {
			courseUi.getCourseById(userName,0,context);
		}
		else if(courseOperationId == 4) {
			courseUi.updateCourse(userName,context);
		}
		
		else if(courseOperationId == 5) {
			courseUi.deleteCourse(userName,context);
		}
		
		else if(courseOperationId == 6) {
			context.getBean(InstructorUi.class).display(userName,context);
		}
		
		else {
			LOGGER.error("Invalid Option. Please select the appropriate option");
		}
		
		
	}
}
