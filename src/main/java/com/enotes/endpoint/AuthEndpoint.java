package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.UserRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Authentication" , description = " All the user Authentication ")
@RequestMapping("/api/v1/auth")
public interface AuthEndpoint {

	@Operation(summary  = "User registration endpoint",tags = { "Authentication" } , description = "User can register here")
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest  userDto,HttpServletRequest servletRequest) throws Exception;
	
	@Operation(summary = "User login endpoint",tags = { "Authentication" }, description = "User/admin can login here")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest  loginRequest) throws Exception;
	
}
