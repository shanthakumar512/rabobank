package com.assignment.rabobankcsp.model;

import java.util.List;
public class ErrorResponse {
	
	 /**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the details
	 */
	public List<String> getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(List<String> details) {
		this.details = details;
	}

	private String message;
	 private List<String> details;	
	
	public ErrorResponse(String message, List<String> details) {
        super();
        this.message = message;
        this.details = details;
    }
  
   
	
}
