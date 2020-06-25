package com.assignment.rabobankcsp.services.validator.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import com.assignment.rabobankcsp.model.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class CustomerStatementParserServiceimpl implements CustomerStatementParserService {
	Logger logger = LoggerFactory.getLogger(CustomerStatementParserServiceimpl.class);
	/**
	 * Method used to parse the json file and returns list of records
	 */
	public List<Record> parseStament(MultipartFile file)
			throws JsonSyntaxException, JsonIOException, IOException, JSONException {
		String fileOriginalName = file.getOriginalFilename();
		File convFile;
		if (file.getOriginalFilename().contains("\\")) {

			Path path = Paths.get(fileOriginalName);

			Path fileName = path.getFileName();
			convFile = new File(System.getProperty("java.io.tmpdir") + "/" + new Date().getTime() + fileName);
		} else
			convFile = new File(System.getProperty("java.io.tmpdir") + "/" + new Date().getTime() + fileOriginalName);

		file.transferTo(convFile);
		logger.info("Uploaded Json file stored in temp");
		return getjsonRecords(convFile);

	}
	
	/**
	 * This method returns string of json arrays to parse the records.
	 */
	public List<Record> getjsonRecords(File convFile) throws JsonMappingException, JsonProcessingException{
		 ObjectMapper objectMapper = new ObjectMapper();
		 logger.info("File converted to String with Json Arrays {}", convFile.getName());
		 String jsonCarArray= readFile(convFile);
		 List<Record> records =objectMapper.readValue(jsonCarArray, new TypeReference<List<Record>>(){});
		 logger.info("Json file successfully parsed to return list of Records.");
		 return records;
	}
	
	/**
	 * This method will convert json file to string
	 * @param convFile
	 * @return string of json input
	 */
	public static String readFile(File convFile) {
	    String result = "";
	    try {
	        @SuppressWarnings("resource")
			BufferedReader br = new BufferedReader(new FileReader(convFile));
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
	}
