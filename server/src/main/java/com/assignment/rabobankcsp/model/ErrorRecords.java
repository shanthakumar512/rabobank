package com.assignment.rabobankcsp.model;

public class ErrorRecords {
	
	private int reference;
	/**
	 * @return the reference
	 */
	public int getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	public void setReference(int reference) {
		this.reference = reference;
	}
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	private String accountNumber;
}
