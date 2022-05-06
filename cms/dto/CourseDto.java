package com.epam.cms.dto;

import java.util.List;


import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class CourseDto {
	
	private int courseId;
	@NotBlank(message = "Course Name cannot be empty")
	private String courseName;
	private InstructorDto instructor;
	@NotBlank(message = "Course Syllabus cannot be empty")
	private String syllabus;
	@JsonIgnore
	private List<AssignmentDto> assignments;
	
	public List<AssignmentDto> getAssignments() {
		return assignments;
	}
	
	public void setAssignments(List<AssignmentDto> assignments) {
		this.assignments = assignments;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public InstructorDto getInstructor() {
		return instructor;
	}


	public void setInstructor(InstructorDto instructor) {
		this.instructor = instructor;
	}


	public String getSyllabus() {
		return syllabus;
	}

	public void setSyllabus(String syllabus) {
		this.syllabus = syllabus;
	}

	
}
