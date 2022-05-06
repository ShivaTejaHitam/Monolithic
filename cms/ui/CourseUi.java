package com.epam.cms.ui;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.epam.cms.services.*;
import com.epam.cms.dto.*;
import com.epam.cms.exceptions.CourseNotFoundException;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.InstructorRepository;

@Component
public class CourseUi implements InstructorOption {

	Scanner scanner = new Scanner(System.in);
	int courseIdForUpdate;
	private static final Logger LOGGER = LogManager.getLogger(CourseUi.class);

	@Autowired
	private CourseService courseService;
	@Autowired
	@Qualifier("instructorRepository")
	private InstructorRepository instructorRepository;

	public void display(String userName, ApplicationContext context) {

		int courseOperationId = -1;
		CourseOperationFactory courseOperationFactory = new CourseOperationFactory();

		while (courseOperationId == -1) {

			new Menu().showMenu(new String[] { "Create New Course", "Show All courses", "Get Course By Id",
					"Update Course", "Delete Course By Id", "Back" });

			try {
				courseOperationId = Integer.parseInt(scanner.nextLine());
				courseOperationFactory.callCourseOperation(courseOperationId, userName, context);
			} catch (Exception exception) {
				LOGGER.error("Invalid Option");
				continue;
			}

			if (courseOperationId == 6) {
				break;
			} else {
				courseOperationId = -1;
			}
		}
	}

	public void createCourse(String username, ApplicationContext context) {

		LOGGER.info("Enter Course Name \n");
		String courseName = scanner.nextLine();

		LOGGER.info("Define Syllabus of the Course");
		String Syllabus = scanner.nextLine();

		CourseDto courseDto = new CourseDto();
		InstructorDto instructor = new InstructorDto();

		instructor = Mapper.convertEntityToDto(instructorRepository.findByUsername(username));
		courseDto.setCourseName(courseName);

		courseDto.setSyllabus(Syllabus);

		courseDto.setInstructor(instructor);

		courseService.save(courseDto);

		context.getBean(CourseUi.class).display(username, context);

	}

	public void showAllCourses(String username, int updateFlag, ApplicationContext context) {
		List<CourseDto> coursesList = new ArrayList<>();
		try {
			 coursesList = courseService.findAll();
			System.out.println("length is " + coursesList.size());
		}
		catch(Exception exception) {
			System.out.println(exception);
		}
		
		
		if (coursesList.size() < 1) {
			LOGGER.info("No Courses with your UserName\n");
		} else {

			LOGGER.info("List of Courses");
			coursesList.stream().forEach(c -> LOGGER.info("\nCourse ID : " + c.getCourseId() + "\nCourse Name : "
					+ c.getCourseName() + "\nInstructorName : " + username + "\nSyllabus : " + c.getSyllabus()));
		}

		if (updateFlag == 0)
			context.getBean(CourseUi.class).display(username, context);

	}

	public void getCourseById(String username, int updateFlag, ApplicationContext context) {
		int courseId = -1;

		while (courseId == -1) {
			try {

				LOGGER.info("Enter course ID of the Course");
				courseId = Integer.parseInt(scanner.nextLine());
				courseIdForUpdate = courseId;

				Optional<CourseDto> courseOptional = courseService.findById(courseId);

				
				CourseDto course = courseOptional.get();
				LOGGER.info("\nCourse ID : " + course.getCourseId() + "\nCourse Name : " + course.getCourseName()
							+ "\nInstructorName : " + username + "\nSyllabus : " + course.getSyllabus());
				
			} 
			
			catch(CourseNotFoundException exception) {
				LOGGER.error(exception);
			}
			
			catch (NumberFormatException ne) {
				LOGGER.error("Please Enter the valid Course ID");
			}
		}

		if (updateFlag == 0)
			context.getBean(CourseUi.class).display(username, context);
	}

	public void updateCourse(String username, ApplicationContext context) {

		showAllCourses(username, 1, context);
		getCourseById(username, 1, context);

		try {
			LOGGER.info("Enter the new Course Name");
			String courseName = scanner.nextLine();

			LOGGER.info("Enter new Syllabus ");
			String syllabus = scanner.nextLine();

			CourseDto courseDto = new CourseDto();
			courseDto.setCourseId(courseIdForUpdate);
			courseDto.setCourseName(courseName);
			courseDto.setSyllabus(syllabus);
			
			courseService.save(courseDto);
		}

		catch (Exception exception) {
			LOGGER.error("Unexpected error Occured");
		}

		context.getBean(CourseUi.class).display(username, context);

	}

	public void deleteCourse(String username, ApplicationContext context) {

		int courseId = -1;

		while (courseId == -1) {
			try {
				LOGGER.info("Enter course ID of the Course");
				courseId = Integer.parseInt(scanner.nextLine());
				courseService.delete(courseId);
			} 
			catch(CourseNotFoundException exception) {
				LOGGER.error(exception);
			}
			catch (Exception exception) {
				LOGGER.error("Invalid Option");
			}
		}

		context.getBean(CourseUi.class).display(username, context);

	}
}
