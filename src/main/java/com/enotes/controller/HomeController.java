package com.enotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PwdResetRequest;
import com.enotes.endpoint.HomeEndpoint;
import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController implements HomeEndpoint {
	
	Logger log=LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;

	@Override
	public ResponseEntity<?> verifyAccount(@RequestParam int uid, @RequestParam String code ) throws Exception{
		log.info("HomeController : verifyAccount() : Execution start");
		boolean verifyAccount = homeService.verifyAccount(uid, code);
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.ACCEPTED);
		}
		log.info("HomeController : verifyAccount : Execution end");
		return CommonUtil.createErrorResponseMessage("Invalid url or url already used", HttpStatus.BAD_REQUEST);
		
	}
	
	@Override
	public ResponseEntity<?> sendEmailForPwdReset(@PathVariable String email,HttpServletRequest servletRequest) throws Exception{
		userService.sendEmailPwdReset(email,servletRequest);
		return CommonUtil.createBuildResponseMessage("password reset link send successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam int uid,@RequestParam String resetCode) throws Exception{
		userService.verifyPasswordResetLink(uid,resetCode);
		return CommonUtil.createBuildResponseMessage("Password reset successfully", HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<?> resetPassword(@RequestBody PwdResetRequest pwdResetRequest) throws Exception{
		userService.resetPwd(pwdResetRequest);
		return CommonUtil.createBuildResponseMessage("pwd reset successfully", HttpStatus.OK);
	}
	
}
