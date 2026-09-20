package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;
import com.enotes.service.AuthService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping("/")
	public ResponseEntity<?> registerUser(@RequestBody UserRequest  userDto,HttpServletRequest servletRequest) throws Exception{
		String url=CommonUtil.getUrl(servletRequest);
		boolean register = authService.register(userDto,url);
		if(register) {
			return CommonUtil.createBuildResponseMessage("registration successful", HttpStatus.CREATED);
		}
		else {
			return CommonUtil.createErrorResponseMessage("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest  loginRequest) throws Exception{
		
		LoginResponse response = authService.login(loginRequest);
		
		if(!ObjectUtils.isEmpty(response)) {
			return CommonUtil.createBuildResponse(response, HttpStatus.OK);
		}
		
		return CommonUtil.createErrorResponseMessage("invalid credential", HttpStatus.BAD_REQUEST);
	}
	
	

}
