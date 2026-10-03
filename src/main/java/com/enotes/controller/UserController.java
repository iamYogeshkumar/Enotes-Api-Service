package com.enotes.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.PasswordChangeReq;
import com.enotes.dto.UserResponse;
import com.enotes.endpoint.UserEndpoint;
import com.enotes.entity.User;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

@RestController
public class UserController implements UserEndpoint {

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserService userService;
	
	
	@Override
	public ResponseEntity<?> getProfile( CustomUserDetails userDetails){
		System.err.println(userDetails.getUsername());
		System.err.println(userDetails.getUser().getFirstName());
		User user = CommonUtil.getLoggedInUser();
		UserResponse response = mapper.map(user, UserResponse.class);
		return CommonUtil.createBuildResponse(response, HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> changePassword( PasswordChangeReq changeReq){
		userService.changePassword(changeReq);
		return CommonUtil.createBuildResponseMessage("password changed successfully", HttpStatus.OK);
	}
}
