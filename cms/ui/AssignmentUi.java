package com.epam.cms.ui;

import java.time.LocalDate;



import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.epam.cms.services.*;
import com.epam.cms.dto.*;
import com.epam.cms.mapper.Mapper;
import com.epam.cms.repositories.InstructorRepository;

@Component
public class AssignmentUi implements InstructorOption {
	
	Scanner  scanner = new Scanner(System.in);
	int assignmentIdForUpdate;
	int courseIdForUpdate;
	private static final Logger LOGGER = LogManager.getLogger(AssignmentUi.class);
	
	@Autowired
	private AssignmentService assignmentService;
	@Autowired
	private CourseService courseService;
	@Autowired
	@Qualifier("instructorRepository")
	private InstructorRepository instructorRepository;
	
	public void display(String userName,ApplicationContext context){
		
		scanner = new Scanner(System.in);
		
		int assignmentOperationId = -1;
		AssignmentOperationFactory assignmentOperationFactory = new AssignmentOperationFactory();
		
		while(assignmentOperationId == -1) {
			
			new Menu().showMenu(new String[] {"Create New Assignment","Show All assignments","Get Assignment By Id","Update Assignment","Delete Assignment By Id","Back"});
			
			try {
				assignmentOperationId = Integer.parseInt(scanner.nextLine());
				assignmentOperationFactory.callAssignmentOperation(assignmentOperationId, userName,context);
			}
			catch(Exception exception) {
				LOGGER.error("Please enter a valid Option");
				continue;
			}
			
			
			if(assignmentOperationId == 6) {
				break;
			}
			else {
				LOGGER.error("Invalid Option. Please select the appropriate option");
				assignmentOperationId = -1;
			}
		}
	}
	
	
	public void  createAssignment(String username, ApplicationContext context) {
		
		LOGGER.info("Enter Course Id \n");
		int courseId = Integer.parseInt(scanner.nextLine());
		
		LOGGER.info("Enter  Assignment Description \n");
		String title = scanner.nextLine();
		
		LOGGER.info("Enter Number of days to complete the Assignment");
		int days = Integer.parseInt(scanner.nextLine());
		LocalDate deadLine = LocalDate.now().plusDays(days);
		
		LOGGER.info("Enter Questions of the Assignment");
		int questionInput = -1;
		
		List<QuestionDto> questionList = new ArrayList<>();
		int totalMarks = 0;
		
		while(questionInput == -1){
			
			QuestionDto question = new QuestionDto();
			
			LOGGER.info("Enter Question :");
			String questionContent = scanner.nextLine();
			
			LOGGER.info("Enter Maximum Marks for Question");
			int questionMarks = Integer.parseInt(scanner.nextLine());
			totalMarks = totalMarks + questionMarks;
			
			question.setDescription(questionContent);
			question.setMaxMarks(questionMarks);			
			questionList.add(question);
			
			
			LOGGER.info("Enter 1 if Done Adding Questions");
			try {
				questionInput = Integer.parseInt(scanner.nextLine());
			}
			catch(Exception exception) {
				LOGGER.error("Invalid Input");
			}
			
			questionInput = questionInput == 1 ? 1 : -1;
			
		}
		
		AssignmentDto assignmentDto = new AssignmentDto();
			
		assignmentDto.setTitle(title);
		assignmentDto.setMaxMarks(totalMarks);
		assignmentDto.setDeadLine(deadLine);
		assignmentDto.setQuestions(questionList);
		
		CourseDto courseDto = courseService.findById(courseId).get();
		InstructorDto instructor = Mapper.convertEntityToDto(instructorRepository.findByUsername(username));
		
		assignmentDto.setInstructor(instructor);
		assignmentDto.setCourse(courseDto);
		
		assignmentService.save(assignmentDto);
		
		context.getBean(AssignmentUi.class).display(username,context);
		
	}
	
	public void showAllAssignments(String username,int updateFlag, ApplicationContext context) {
		
		List<AssignmentDto> assignmentsList = assignmentService.findAll();
		
		if(assignmentsList.isEmpty()){
			LOGGER.info("No Assignments with your UserName\n");
		}
		else {
			
			LOGGER.info("List of Assignments");
			assignmentsList.stream().forEach(c -> {
				LOGGER.info("\nAssignment Id : " + c.getAssignmentId() + "\nCourse ID : " + c.getCourse().getCourseId() + "\ninstructorName : " + c.getInstructor().getUsername() + "\nAssignment Title : " + c.getTitle() + "\nDeadLine : " + c.getDeadLine() + "\nMaximum Marks : " + c.getMaxMarks());
				
				LOGGER.info("List of Questions\n");
				for(QuestionDto question : c.getQuestions()) {
					LOGGER.info("Question : " + question.getDescription() + "\nMaxMarks : " + question.getMaxMarks() +"\n");
				}
			});
			
		}
		
		if(updateFlag == 0)
			context.getBean(AssignmentUi.class).display(username,context);
		
	}
	
	
	public void getAssignmentById(String username,int updateFlag, ApplicationContext context) {
		int assignmentId = -1;
		
		while(assignmentId == -1) {
			try {
				LOGGER.info("Enter assignment ID of the Assignment");
				assignmentId = Integer.parseInt(scanner.nextLine());
				assignmentIdForUpdate = assignmentId;
				
				AssignmentDto assignment = assignmentService.findById(assignmentId).get();
				
				if(assignment == null) {
					LOGGER.info("Assignment Does not Exist");
				}
				else {
					LOGGER.info("\nAssignment Id : " + assignment.getAssignmentId() + "\nCourse ID : " + assignment.getCourse().getCourseId() + "\ninstructorName : " + assignment.getInstructor().getUsername() + "\nAssignment Description : " + assignment.getTitle() + "\nDeadLine : " + assignment.getDeadLine() + "\nMaximum Marks : " + assignment.getMaxMarks());
					courseIdForUpdate = assignment.getCourse().getCourseId();
					
					LOGGER.info("List of Questions\n");
					for(QuestionDto question : assignment.getQuestions()) {
						LOGGER.info("Question : " + question.getDescription() + "\nMaxMarks : " + question.getMaxMarks() +"\n");
					}
					
				}
			}
			catch(Exception exception) {
				LOGGER.error("Please Enter the valid ID");
			}
		}
		if(updateFlag == 0)
			context.getBean(AssignmentUi.class).display(username,context);
	}
	
	public void updateAssignment(String username, ApplicationContext context) {
		
		showAllAssignments(username,1,context);
		getAssignmentById(username,1,context);
		
		try {
			LOGGER.info("Enter the new Assignment Title");
			String title = scanner.nextLine();
			
			LOGGER.info("Enter the no of days after assignment has to be submitted");
			int days = Integer.parseInt(scanner.nextLine());
			LocalDate deadLine = LocalDate.now().plusDays(days);
			
			LOGGER.info("Enter Questions of the Assignment");
			int questionInput = -1;
			
			List<QuestionDto> questionList = new ArrayList();
			int totalMarks = 0;
			
			while(questionInput == -1){
				
				QuestionDto question = new QuestionDto();
				
				LOGGER.info("Enter Question :");
				String questionContent = scanner.nextLine();
				
				LOGGER.info("Enter Maximum Marks for Question");
				int questionMarks = Integer.parseInt(scanner.nextLine());
				totalMarks = totalMarks + questionMarks;
				
				question.setDescription(questionContent);
				question.setMaxMarks(questionMarks);
				
				questionList.add(question);
				
				
				LOGGER.info("Enter 1 if Done Adding Questions");
				try {
					questionInput = Integer.parseInt(scanner.nextLine());
				}
				catch(Exception exception) {
					LOGGER.error("Invalid Input");
				}
				
				questionInput = questionInput == 1 ? 1 : -1;
				
			}
			
			
			AssignmentDto assignmentDto = new AssignmentDto();
			assignmentDto.setAssignmentId(assignmentIdForUpdate);
			assignmentDto.setTitle(title);
			assignmentDto.setDeadLine(deadLine);
			assignmentDto.setMaxMarks(totalMarks);
			assignmentDto.setQuestions(questionList);
			
			assignmentService.save(assignmentDto);
		}
		
		catch(Exception exception) {
			LOGGER.error("Invalid Input");
		}
		
		context.getBean(AssignmentUi.class).display(username,context);
		
	}
	
	public void deleteAssignment(String username, ApplicationContext context) {
		
		int assignmentId = -1;
		
		while(assignmentId == -1) {
			try {
				LOGGER.info("Enter assignment ID of the Assignment");
				assignmentId = Integer.parseInt(scanner.nextLine());
				
				assignmentService.delete(assignmentId);
			}
			catch(Exception exception) {
				LOGGER.error("Please Enter the valid Assignment ID");
			}
		}
		
		context.getBean(AssignmentUi.class).display(username,context);
		
	}
}
