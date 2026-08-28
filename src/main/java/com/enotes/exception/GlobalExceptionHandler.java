package com.enotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.enotes.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exception){
//		return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		return CommonUtil.createErrorResponseMessage(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(Exception exception){
		log.error("GlobalExceptionHandler :: handleResourceNotFoundException "+exception.getMessage());
//		return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
		
		return CommonUtil.createErrorResponseMessage(exception.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException exception){
		log.error("GlobalExceptionHandler :: ValidationException "+exception.getMessage());
//		return new ResponseEntity<>(exception.getError(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponse(exception.getError(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ExistDataException.class)
	public ResponseEntity<?> handleExistDataException(ExistDataException exception){
		log.error("GlobalExceptionHandler :: ExistDataException "+exception.getMessage());
//		return new ResponseEntity<>(exception.getMessage(),HttpStatus.CONFLICT);
		return CommonUtil.createErrorResponseMessage(exception.getMessage(), HttpStatus.CONFLICT);
	}
	
//	HttpMessageNotReadableException
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<?> handlHttpMessageNotReadableException(HttpMessageNotReadableException exception){
		log.error("GlobalExceptionHandler :: HttpMessageNotReadableException "+exception.getMessage());
//		return new ResponseEntity<>(exception.getMessage(),HttpStatus.BAD_REQUEST);
		return CommonUtil.createErrorResponseMessage(exception.getMessage(),HttpStatus.BAD_REQUEST);
	}
}
