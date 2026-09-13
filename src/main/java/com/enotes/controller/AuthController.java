package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.UserDto;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/user")
public class AuthController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<?> register(@RequestBody UserDto  userDto){
		boolean register = userService.register(userDto);
		if(register) {
			return CommonUtil.createBuildResponseMessage("registration successful", HttpStatus.CREATED);
		}
		else {
			return CommonUtil.createErrorResponseMessage("Registration failed", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
