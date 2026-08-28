package com.enotes.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enotes.handler.GenericResponse;

public class CommonUtil {

	public static ResponseEntity<?> createBuildResponse(Object data,HttpStatus status){
		
		GenericResponse response=GenericResponse.builder()
		                         .responseStatus(status)
		                         .data(data)
		                         .status(status.value())
		                         .msg("success")
		                        .build();
		
		return response.create();
		
	}
	
    public static ResponseEntity<?> createBuildResponseMessage(String message,HttpStatus status){
		
		GenericResponse response= GenericResponse.builder()
		                         .responseStatus(status)
		                         .status(status.value())
		                         .msg(message)
		                         .build();
		
		return response.create();
		
	}
    
    public static ResponseEntity<?> createErrorResponse(Object data,HttpStatus status){
		
		GenericResponse response= GenericResponse.builder()
				                 .data(data)
		                         .responseStatus(status)
		                         .status(status.value())
		                         .msg("failed")
		                         .build();
		
		return response.create();
		
	}
    
     public static ResponseEntity<?> createErrorResponseMessage(String message,HttpStatus status){
		
		GenericResponse response= GenericResponse.builder()
		                         .responseStatus(status)
		                         .status(status.value())
		                         .msg(message)
		                         .build();
		
		return response.create();
		
	}
	
}
