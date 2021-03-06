/**
 * 
 */
package com.assignment.rabobankcsp.services.parser;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.assignment.rabobankcsp.model.Record;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

/**
 * @author Shanthakumar
 *
 */
public interface CustomerStatementParserService {
	
	public List<Record> parseStament(MultipartFile file) throws JsonSyntaxException, JsonIOException, IOException, JSONException;
	
	public List<Record> getjsonRecords(File convFile) throws JsonMappingException, JsonProcessingException;
}
