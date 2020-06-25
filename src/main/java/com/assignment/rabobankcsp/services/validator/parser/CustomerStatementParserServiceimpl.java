package com.assignment.rabobankcsp.services.validator.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import ch.qos.logback.core.net.SyslogOutputStream;

import com.assignment.rabobankcsp.model.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@Component
public class CustomerStatementParserServiceimpl implements CustomerStatementParserService {
	
	@SuppressWarnings("unchecked")
	public List<Record> parseStament(MultipartFile file) throws JsonSyntaxException, JsonIOException, IOException, JSONException{
		 File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+new Date().getTime()+file.getOriginalFilename());
		 file.transferTo(convFile);
		 return getjsonRecords(convFile);
		 
	}
	
	public List<Record> getjsonRecords(File convFile) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		 String jsonCarArray= readFile(convFile);
		 List<Record> records = new ArrayList<>();
		 records=objectMapper.readValue(jsonCarArray, new TypeReference<List<Record>>(){});
		 return records;
	}

	public static String readFile(File convFile) {
	    String result = "";
	    try {
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
