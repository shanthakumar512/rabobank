package com.assignment.rabobankcsp.model;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Shanthakumar
 *
 */
@Component
public class ProcessedStatement {
	
	private String result;
	
	
	private List<ErrorRecords> errorRecords;

	
	public List<ErrorRecords> getErrorRecords() {
		return errorRecords;
	}

	public void setErrorRecords(List<ErrorRecords> errorRecords) {
		this.errorRecords = errorRecords;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	
}