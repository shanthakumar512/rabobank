package com.assignment.rabobankcsp.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Record {
	private int reference;
	private String accountNumber;
	private double mutation;
	private double startBalance;
	private String description;
	private double endBalance;
	
	public Record() {
		
	}
	
	
	/**
	 * @param reference
	 * @param accountNumber
	 * @param mutation
	 * @param startBalance
	 * @param description
	 * @param endBalance
	 */
	public Record(int reference, String accountNumber, double mutation, double startBalance, String description,
			double endBalance) {
		this.reference = reference;
		this.accountNumber = accountNumber;
		this.mutation = mutation;
		this.startBalance = startBalance;
		this.description = description;
		this.endBalance = endBalance;
	}


	/**
	 * @return the reference
	 */
	@JsonProperty("Reference")
	public int getReference() {
		return reference;
	}
	/**
	 * @param reference the reference to set
	 */
	@JsonProperty("Reference")
	public void setReference(int reference) {
		this.reference = reference;
	}
	/**
	 * @return the accountNumber
	 */
	@JsonProperty("AccountNumber")
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	@JsonProperty("AccountNumber")
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the mutation
	 */
	@JsonProperty("Mutation")
	public double getMutation() {
		return mutation;
	}
	/**
	 * @param mutation the mutation to set
	 */
	@JsonProperty("Mutation")
	public void setMutation(double mutation) {
		this.mutation = mutation;
	}
	/**
	 * @return the startBalance
	 */
	@JsonProperty("Start Balance")
	public double getStartBalance() {
		return startBalance;
	}
	/**
	 * @param startBalance the startBalance to set
	 */
	@JsonProperty("Start Balance")
	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}
	/**
	 * @return the description
	 */
	@JsonProperty("Description")
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	@JsonProperty("Description")
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the endBalance
	 */
	@JsonProperty("End Balance")
	public double getEndBalance() {
		return endBalance;
	}
	/**
	 * @param endBalance the endBalance to set
	 */
	@JsonProperty("End Balance")
	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}
	
	

}
