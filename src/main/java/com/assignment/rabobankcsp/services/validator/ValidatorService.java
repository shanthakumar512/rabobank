/**
 * 
 */
package com.assignment.rabobankcsp.services.validator;

import java.util.List;

import com.assignment.rabobankcsp.model.ErrorRecords;
import com.assignment.rabobankcsp.model.Record;

/**
 * @author Shanthakumar
 *
 */

public interface ValidatorService {

	public List<ErrorRecords> getDuplicateRecords(List<Record> records);
	
	public List<ErrorRecords> getEndBalanceErrorRecords(List<Record> records);	
}
