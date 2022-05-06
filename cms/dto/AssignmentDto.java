package com.epam.cms.dto;

import java.util.List;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;

public class AssignmentDto {
	
	private int assignmentId;
	private CourseDto course;
	private InstructorDto instructor;
	@NotBlank(message = "Assignment Title cannot be empty")
	private String title;
	@NotNull(message = "Assignment DeadLine cannot be empty")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate deadLine;
	private int maxMarks;
	@NotNull(message = "Questions cannot be empty")
	private List<QuestionDto> questions;
	
	public int getAssignmentId() {
		return assignmentId;
	}

	public void setAssignmentId(int assignmentId) {
		this.assignmentId = assignmentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDeadLine() {
		return deadLine;
	}

	public void setDeadLine(LocalDate deadLine) {
		this.deadLine = deadLine;
	}

	public int getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}

	public CourseDto getCourse() {
		return course;
	}

	public void setCourse(CourseDto course) {
		this.course = course;
	}

	public InstructorDto getInstructor() {
		return instructor;
	}

	public void setInstructor(InstructorDto instructor) {
		this.instructor = instructor;
	}

	public List<QuestionDto> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDto> questions) {
		questions.forEach(question -> question.setAssignment(this));
		this.questions = questions;
	}

}
