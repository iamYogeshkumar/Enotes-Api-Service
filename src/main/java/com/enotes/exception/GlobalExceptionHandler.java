package com.enotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exception){
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception exception){
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException "+exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException exception){
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException "+exception.getMessage());
		return new ResponseEntity<>(exception.getError(),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException exception){
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException "+exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
	}
	
//	HttpMessageNotReadableException
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handlHttpMessageNotReadableException(HttpMessageNotReadableException exception){
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException "+exception.getMessage());
		return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
