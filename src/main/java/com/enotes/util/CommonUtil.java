package com.enotes.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.UserResponse;
import com.enotes.entity.User;
import com.enotes.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;

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

     public static String getContentType(String originalFileName) {
    	    String extension = FilenameUtils.getExtension(originalFileName).toLowerCase();

    	    switch (extension) {
    	        case "pdf":
    	            return "application/pdf";

    	        case "jpeg":
    	            return "image/jpeg";

    	        case "png":
    	            return "image/png";

    	        case "xls":
    	            return "application/vnd.ms-excel";

    	        case "xlsx":
    	            return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    	        default:
    	            return "application/octet-stream";
    	    }
    	}

	public static String getUrl(HttpServletRequest servletRequest) {
		String apiUrl = servletRequest.getRequestURL().toString();  //http://localhost:8080/api/v1/home
		apiUrl=apiUrl.replace(servletRequest.getServletPath(), "");   //http://localhost
		return apiUrl;
	}
	
	
	public static  User getLoggedInUser() {
		try {
			CustomUserDetails loggedInUser=(CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			return loggedInUser.getUser();
			
		} catch (Exception e) {
			throw e;
	}
	
  }
} 
