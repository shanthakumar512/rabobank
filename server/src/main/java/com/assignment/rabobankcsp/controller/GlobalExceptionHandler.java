package com.assignment.rabobankcsp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.assignment.rabobankcsp.constant.StatusConstants;
import com.assignment.rabobankcsp.exceptions.FileNotSupportedException;
import com.assignment.rabobankcsp.model.ErrorResponse;
import com.assignment.rabobankcsp.model.ProcessedStatement;
/**
 * 
 * @author Shanthakumar
 *
 */

@ControllerAdvice
public class GlobalExceptionHandler   extends ResponseEntityExceptionHandler{
	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
    @ExceptionHandler(MultipartException.class)
    public String handleError1(MultipartException e, RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("message", e.getCause().getMessage());
        return "redirect:/uploadStatus";
    }
    
    @ExceptionHandler(IOException.class)
	public @ResponseBody ErrorResponse handleIOException( IOException exception) {
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
    	ErrorResponse errorResponse = new ErrorResponse("IO Exception", details);
    	
		logger.error("IO Exception Occured: {}", exception.getMessage());
		return errorResponse;
	}
    
    @ExceptionHandler(FileNotSupportedException.class)
	public @ResponseBody ResponseEntity<ErrorResponse> handleFileException( FileNotSupportedException exception) {
    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
    	ErrorResponse errorResponse = new ErrorResponse("FileNotSupportedException", details);
    	
		logger.error("FileNotSupportedException: {}", details);
		return new ResponseEntity<>(errorResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
	public @ResponseBody final ResponseEntity<ErrorResponse> handleBadRequestException(final ResponseStatusException exception,
			final HttpServletRequest request) {

    	 List<String> details = new ArrayList<>();
         details.add(exception.getLocalizedMessage());
         ErrorResponse error = new ErrorResponse("BAD_REQUEST", details);
         return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
   	public @ResponseBody final ResponseEntity<ErrorResponse> handleInternalServer(final ResponseStatusException exception,
   			final HttpServletRequest request) {

       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse("INTERNAL_SERVER_ERROR", details);
            return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
   	}
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
   	public @ResponseBody final ResponseEntity<ErrorResponse> handleMethodNotAllowedException(final ResponseStatusException exception,
   			final HttpServletRequest request) {

       	 List<String> details = new ArrayList<>();
            details.add(exception.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse("METHOD_NOT_ALLOWED", details);
            return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
            
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