package com.assignment.rabobankcsp.services.validator;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.assignment.rabobankcsp.model.ErrorRecords;
import com.assignment.rabobankcsp.model.Record;
/**
 * 
 * @author Shanthakumar
 * Class to validate the records for duplicate and end balance issues
 */
@Component
public class ValidatorServiceImpl implements ValidatorService {

	Logger logger = LoggerFactory.getLogger(ValidatorServiceImpl.class);
	/**
	 * @return list<records> the records having duplicate reference 
	 */
	@Override
	public List<ErrorRecords> getDuplicateRecords(List<Record> records) {
		Map<Integer, Record> uniqeRecords = new HashMap<Integer, Record>();
		List<ErrorRecords> duplicateRecords = new ArrayList<ErrorRecords>();
		for (Record record : records) {
			
			if (uniqeRecords.containsKey(record.getReference())) {
				logger.info("Record reference is duplicate", record.getReference());
				ErrorRecords e = new ErrorRecords();
				e.setReference(record.getReference());
				e.setAccountNumber(record.getAccountNumber());
				duplicateRecords.add(e);
			} else {
				logger.info("Record reference is Unique", record.getReference());
				uniqeRecords.put(record.getReference(), record);
			}
		}
		List<ErrorRecords> finalDuplicateRecords = new ArrayList<ErrorRecords>();
		finalDuplicateRecords.addAll(duplicateRecords);
		
		for (ErrorRecords record : duplicateRecords) {
			if (null != uniqeRecords.get(record.getReference())) {
				logger.info("Record with duplicate refernce is removed from Unique list", record.getReference());
				uniqeRecords.remove(record.getReference());
			}
		}
		return finalDuplicateRecords;
	}
	/**
	 * @return List<Records> if startbalance - mutation != endbalance then
	 *         endbalance is wrong and the list is  returned.
	 */

	@Override
	public List<ErrorRecords> getEndBalanceErrorRecords(List<Record> records) {
		
		List<ErrorRecords> endBalanceErrorRecords = new ArrayList<ErrorRecords>();
		for (Record record : records) {
			if (Math.round((record.getStartBalance() + record.getMutation()) - Math.round(record.getEndBalance())) != 0) {
				logger.info("Error in End balance for record :", record.getReference());
				ErrorRecords e = new ErrorRecords();
				e.setReference(record.getReference());
				e.setAccountNumber(record.getAccountNumber());
				endBalanceErrorRecords.add(e);
				logger.info("Record added to end balance error count");
			}
		}
		return endBalanceErrorRecords;
	}

}
