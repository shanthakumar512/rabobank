package com.assignment.RobobankCSP;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.WebApplicationContext;

import com.assignment.rabobankcsp.RobobankCspWebAppApplication;
import com.assignment.rabobankcsp.model.ErrorRecords;
import com.assignment.rabobankcsp.model.Record;
import com.assignment.rabobankcsp.services.parser.CustomerStatementParserService;
import com.assignment.rabobankcsp.services.validator.ValidatorService;
import com.assignment.rabobankcsp.services.validator.ValidatorServiceImpl;

@SpringBootTest(classes = RobobankCspWebAppApplication.class)
class RobobankCspWebAppApplicationTests {
	
	@Autowired
	private CustomerStatementParserService customerStatementParserService;
	
	@Test
    public void parseJson() throws Exception {

       String jsonarr= "[{\r\n" + 
       		"		\"Reference\": 139524,\r\n" + 
       		"		\"AccountNumber\": \"NL43AEGO0773393871\",\r\n" + 
       		"		\"Description\": \"Flowers from Jan Bakker\",\r\n" + 
       		"		\"Start Balance\": 99.44,\r\n" + 
       		"		\"Mutation\": \"+41.23\",\r\n" + 
       		"		\"End Balance\": 140.67\r\n" + 
       		"	},\r\n" + 
       		"	{\r\n" + 
       		"		\"Reference\": 179430,\r\n" + 
       		"		\"AccountNumber\": \"NL93ABNA0585619023\",\r\n" + 
       		"		\"Description\": \"Clothes for Vincent Bakker\",\r\n" + 
       		"		\"Start Balance\": 23.96,\r\n" + 
       		"		\"Mutation\": -27.43,\r\n" + 
       		"		\"End Balance\": -10.00\r\n" + 
       		"	}]";
       
       
       try {
           FileWriter file = new FileWriter("./Records.json");
           file.write(jsonarr);
           file.close();
        } catch (IOException e) {
           e.printStackTrace();
        }
       File f = new File("./Records.json");
       customerStatementParserService.getjsonRecords(f);
       
    }

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
