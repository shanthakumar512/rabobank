package com.assignment.RobobankCSP;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.assignment.rabobankcsp.RobobankCspWebAppApplication;
import com.assignment.rabobankcsp.model.ErrorRecords;
import com.assignment.rabobankcsp.model.Record;
import com.assignment.rabobankcsp.services.validator.ValidatorService;
import com.assignment.rabobankcsp.services.validator.ValidatorServiceImpl;

@SpringBootTest(classes = RobobankCspWebAppApplication.class)
class RobobankCspWebAppApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void getDuplicateRecordsTestCaseWithDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<ErrorRecords> duplicateRecords = validatorServiceImpl.getDuplicateRecords(inputList);
		assertEquals(1, duplicateRecords.size());
	}

	/**
	 * Type : Negative 
	 * scenario : Duplicate check in given Rabobank Customer
	 * Statement
	 */
	@Test
	public void getDuplicateRecordsTestCaseWithOutDuplilcate() {
		List<Record> inputList = Arrays.asList(
				new Record(789456, "NL69ABNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 24.98),
				new Record(123654, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 59.61));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<ErrorRecords> duplicateRecords = validatorServiceImpl.getDuplicateRecords(inputList);
		assertEquals(0, duplicateRecords.size());

	}

	/**
	 * Type : Positive 
	 * scenario : EndBalance validation in given Rabobank Customer
	 * Statement
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithWrongValue() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69eBNA0433647324", 66.72, -41.74, "Tickets for Willem Theuß", 50.98),
				new Record(172833, "NL43AEGO0773393871", 16.52, +43.09, "Tickets for Willem Theuß", 25.80));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<ErrorRecords> endBalanceErrorRecords = validatorServiceImpl.getEndBalanceErrorRecords(inputList);
		assertEquals(inputList.size(), endBalanceErrorRecords.size());

	}

	/**
	 * Type : Negative 
	 * scenario : EndBalance validation in given Rabobank Customer
	 * Statement
	 */
	@Test
	public void getEndBalanceErrorRecordsTestCaseWithCorrectValue() {
		List<Record> inputList = Arrays.asList(
				new Record(172833, "NL69ABNA0433647324", 20, -15, "Tickets for Willem Theuß", 5),
				new Record(172833, "NL43AEGO0773393871", 30, 30, "Tickets for Willem Theuß", 60));
		ValidatorService validatorServiceImpl = new ValidatorServiceImpl();
		List<ErrorRecords> endBalanceErrorRecords = validatorServiceImpl.getEndBalanceErrorRecords(inputList);
		assertEquals(0, endBalanceErrorRecords.size());
	}
}
