package com.assignment.rabobankcsp.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.assignment.rabobankcsp.constant.StatusConstants;
import com.assignment.rabobankcsp.exceptions.FileNotSupportedException;
import com.assignment.rabobankcsp.model.ProcessedStatement;
import com.assignment.rabobankcsp.model.ErrorRecords;
import com.assignment.rabobankcsp.model.Record;
import com.assignment.rabobankcsp.services.parser.CustomerStatementParserService;
import com.assignment.rabobankcsp.services.validator.ValidatorService;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

//@CrossOrigin(origins = "http://localhost:4200")
@Controller
@RequestMapping("/")
public class CustomerStatementProcessorController {

	@Autowired
	private ValidatorService validatorService;

	@Autowired
	private CustomerStatementParserService customerStatementParserService;

	Logger logger = LoggerFactory.getLogger(CustomerStatementProcessorController.class);

	@PostMapping("rabobank/savefile")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file)
			throws JsonSyntaxException, JsonIOException, IOException, JSONException, FileNotSupportedException {
		if (!file.isEmpty()) {
			customerStatementParserService.saveUploadedFile(file);
			return ResponseEntity.status(HttpStatus.OK).body("File Uploaded");
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Failed to upload!");
		}
	}

	@GetMapping(value = "/rabobank/processStatement", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProcessedStatement processStatements() throws Exception {

		File file = new File("C:\\Users\\Admin\\AppData\\Local\\Temp\\Statement.json");
		List<Record> extractedRecords = customerStatementParserService.getjsonRecords(file);
		List<ErrorRecords> duplicateRecords = new ArrayList<>();
		List<ErrorRecords> endBalanceRecords = new ArrayList<>();
		duplicateRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
		logger.info("Number of duplicate References in Statement {}", duplicateRecords.size());
		endBalanceRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
		logger.info("Number of records with End Balance Issue {}", duplicateRecords.size());
		ProcessedStatement processedStatement = new ProcessedStatement();
		;
		if (!duplicateRecords.isEmpty() && endBalanceRecords.isEmpty()) {
			processedStatement.setResult(StatusConstants.DUPLICATE_REFERENCE);
			processedStatement.setErrorRecords(duplicateRecords);
		} else if (duplicateRecords.isEmpty() && !endBalanceRecords.isEmpty()) {
			processedStatement.setResult(StatusConstants.INCORRECT_END_BALANCE);
			processedStatement.setErrorRecords(endBalanceRecords);
		} else if (!duplicateRecords.isEmpty() && !endBalanceRecords.isEmpty()) {
			processedStatement.setResult(StatusConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
			processedStatement.setErrorRecords(duplicateRecords);
			processedStatement.setErrorRecords(endBalanceRecords);
			logger.info("Number of Records with duplicate reference and  incorrect end balance {}",
					endBalanceRecords.size() + duplicateRecords.size());
		} else {
			logger.debug("Request successful");
			processedStatement.setResult(StatusConstants.SUCCESSFUL);
		}

		return processedStatement;
	}

	@RequestMapping(value = "/")
	public String index() {
		logger.info("controller entered index() ");
		return "forward:/index.html";
	}

	@PostMapping(value = "/rabobank/processStatement", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ProcessedStatement processStatements(@RequestParam("file") MultipartFile multipart)
			throws Exception {
		return retreiveRecords(multipart);
	}

	private ProcessedStatement retreiveRecords(MultipartFile multipart)
			throws JsonSyntaxException, JsonIOException, IOException, JSONException {
		ProcessedStatement processedStatement = new ProcessedStatement();
		logger.info("File Type {}", multipart.getContentType());
		if (!multipart.isEmpty()) {
			if (multipart.getContentType().equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
				List<Record> extractedRecords = customerStatementParserService.parseStament(multipart);
				logger.info("Number of Transactions in Statement {}", extractedRecords.size());
				List<ErrorRecords> duplicateRecords = new ArrayList<>();
				List<ErrorRecords> endBalanceRecords = new ArrayList<>();
				duplicateRecords.addAll(validatorService.getDuplicateRecords(extractedRecords));
				logger.info("Number of duplicate References in Statement {}", duplicateRecords.size());
				endBalanceRecords.addAll(validatorService.getEndBalanceErrorRecords(extractedRecords));
				logger.info("Number of records with End Balance Issue {}", duplicateRecords.size());
				if (!duplicateRecords.isEmpty() && endBalanceRecords.isEmpty()) {
					processedStatement.setResult(StatusConstants.DUPLICATE_REFERENCE);
					processedStatement.setErrorRecords(duplicateRecords);
				} else if (duplicateRecords.isEmpty() && !endBalanceRecords.isEmpty()) {
					processedStatement.setResult(StatusConstants.INCORRECT_END_BALANCE);
					processedStatement.setErrorRecords(endBalanceRecords);
				} else if (!duplicateRecords.isEmpty() && !endBalanceRecords.isEmpty()) {
					processedStatement.setResult(StatusConstants.DUPLICATE_REFERENCE_INCORRECT_END_BALANCE);
					processedStatement.setErrorRecords(duplicateRecords);
					processedStatement.setErrorRecords(endBalanceRecords);
					logger.info("Number of Records with duplicate reference and  incorrect end balance {}",
							endBalanceRecords.size() + duplicateRecords.size());
				} else {
					logger.debug("Request successful");
					processedStatement.setResult(StatusConstants.SUCCESSFUL);
				}

			} else {
				logger.error("Bad request. Exception while parsing Json");
				processedStatement.setResult(StatusConstants.BAD_REQUEST);
			}

		} else {
			logger.error("Bad request. Empty Json file");
			processedStatement.setResult(StatusConstants.BAD_REQUEST);
		}
		return processedStatement;

	}
}
