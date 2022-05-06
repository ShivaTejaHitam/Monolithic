package com.epam.cms.dto;

import javax.validation.constraints.NotBlank;


import com.fasterxml.jackson.annotation.JsonIgnore;


public class QuestionDto {
	
	private int questionId;
	@NotBlank
	private String description;
	@NotBlank
	private int maxMarks;
	@JsonIgnore
	private AssignmentDto assignment;
	

	public AssignmentDto getAssignment() {
		return assignment;
	}

	public void setAssignment(AssignmentDto assignment) {
		this.assignment = assignment;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	
	
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxMarks() {
		return maxMarks;
	}

	public void setMaxMarks(int maxMarks) {
		this.maxMarks = maxMarks;
	}
	
	
}
