package com.assignment.rabobankcsp.services.validator;

import java.util.*;

import org.springframework.stereotype.Component;

import com.assignment.rabobankcsp.model.ErrorRecords;
import com.assignment.rabobankcsp.model.Record;

@Component
public class ValidatorServiceImpl implements ValidatorService {

	@Override
	public List<ErrorRecords> getDuplicateRecords(List<Record> records) {
		Map<Integer, Record> uniqeRecords = new HashMap<Integer, Record>();
		List<ErrorRecords> duplicateRecords = new ArrayList<ErrorRecords>();
		for (Record record : records) {
			if (uniqeRecords.containsKey(record.getReference())) {
				ErrorRecords e = new ErrorRecords();
				e.setReference(record.getReference());
				e.setAccountNumber(record.getAccountNumber());
				duplicateRecords.add(e);
				
			} else {
				uniqeRecords.put(record.getReference(), record);
			}
		}
		List<ErrorRecords> finalDuplicateRecords = new ArrayList<ErrorRecords>();
		finalDuplicateRecords.addAll(duplicateRecords);
		for (ErrorRecords record : duplicateRecords) {
			if (null != uniqeRecords.get(record.getReference())) {
//				int ref=record.getReference();
//				finalDuplicateRecords.add(ref));
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
				ErrorRecords e = new ErrorRecords();
				e.setReference(record.getReference());
				e.setAccountNumber(record.getAccountNumber());
				
				endBalanceErrorRecords.add(e);
			}
		}
		return endBalanceErrorRecords;
	}

}
