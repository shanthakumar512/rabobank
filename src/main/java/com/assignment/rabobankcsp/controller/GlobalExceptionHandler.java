package com.assignment.rabobankcsp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assignment.rabobankcsp.constant.StatusConstants;
import com.assignment.rabobankcsp.model.ProcessedStatement;
/**
 * 
 * @author Shanthakumar
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler {
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/uploadStatus";
    }
    
    @ExceptionHandler(IOException.class)
	public @ResponseBody ProcessedStatement handleIOException( IOException ex) {
		ProcessedStatement processedStatement = new ProcessedStatement();
		processedStatement.setResult(StatusConstants.IO_EXCEPTION);
		logger.error("IO Exception Occured: {}", ex.getMessage());
		return processedStatement;
	}
    
    @ExceptionHandler(JSONException.class)
   	public @ResponseBody ProcessedStatement handleJSONException( JSONException ex) {
   		ProcessedStatement processedStatement = new ProcessedStatement();
   		processedStatement.setResult(StatusConstants.JSON_EXCEPTION);
   		logger.error("IO Exception Occured: {}", ex.getMessage());
   		return processedStatement;
   	}
    
    @ExceptionHandler(Exception.class)
	public @ResponseBody ProcessedStatement handleException(HttpServletRequest request, Exception ex) {
		ProcessedStatement processedStatement = new ProcessedStatement();
		processedStatement.setResult(StatusConstants.INTERNAL_SERVER_ERROR);
		logger.error("Exception Occured: {}", ex.getMessage());
		return processedStatement;
	}

       
}