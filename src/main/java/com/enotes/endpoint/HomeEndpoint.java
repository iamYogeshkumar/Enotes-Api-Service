package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.enotes.dto.PwdResetRequest;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam int uid, @RequestParam String code ) throws Exception;

	@GetMapping("/send-email/{email}")
	public ResponseEntity<?> sendEmailForPwdReset(@PathVariable String email,HttpServletRequest servletRequest) throws Exception;
	
	@GetMapping("/verify-pwd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam int uid,@RequestParam String resetCode) throws Exception;
	
	@PostMapping("/reset-pwd")
	public ResponseEntity<?> resetPassword(@RequestBody PwdResetRequest pwdResetRequest) throws Exception;
	
}
