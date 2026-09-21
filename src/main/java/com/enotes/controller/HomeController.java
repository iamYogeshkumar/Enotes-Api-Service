package com.enotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PwdResetRequest;
import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;

	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam int uid, @RequestParam String code ) throws Exception{
		boolean verifyAccount = homeService.verifyAccount(uid, code);
		if(verifyAccount) {
			return CommonUtil.createBuildResponseMessage("Account verification success", HttpStatus.ACCEPTED);
		}
		return CommonUtil.createErrorResponseMessage("Invalid url or url already used", HttpStatus.BAD_REQUEST);
		
	}
	
	@GetMapping("/send-email/{email}")
	public ResponseEntity<?> sendEmailForPwdReset(@PathVariable String email,HttpServletRequest servletRequest) throws Exception{
		userService.sendEmailPwdReset(email,servletRequest);
		return CommonUtil.createBuildResponseMessage("password reset link send successfully", HttpStatus.OK);
	}
	
	@GetMapping("/verify-pwd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam int uid,@RequestParam String resetCode) throws Exception{
		userService.verifyPasswordResetLink(uid,resetCode);
		return CommonUtil.createBuildResponseMessage("Password reset successfully", HttpStatus.OK);
	}
	
	@PostMapping("/reset-pwd")
	public ResponseEntity<?> resetPassword(@RequestBody PwdResetRequest pwdResetRequest) throws Exception{
		userService.resetPwd(pwdResetRequest);
		return CommonUtil.createBuildResponseMessage("pwd reset successfully", HttpStatus.OK);
	}
	
}
