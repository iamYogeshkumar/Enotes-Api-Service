package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.enotes.dto.PwdResetRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "Home Api" , description = "All Home API")
@RequestMapping("/api/v1/home")
public interface HomeEndpoint {

	@Operation(summary = "Verify user account ",tags = { "Home Api" },description = " User account verification after registration")
	@GetMapping("/verify")
	public ResponseEntity<?> verifyAccount(@RequestParam int uid, @RequestParam String code ) throws Exception;

	@Operation(summary = "send email for password reset ",tags = { "Home Api" },description = "sending  alink to user|admin's email to reset there password")
	@GetMapping("/send-email/{email}")
	public ResponseEntity<?> sendEmailForPwdReset(@PathVariable String email,HttpServletRequest servletRequest) throws Exception;
	
	@Operation(summary = "verify password reset link ",tags = { "Home Api" },description = "password verification ")
	@GetMapping("/verify-pwd-link")
	public ResponseEntity<?> verifyPasswordResetLink(@RequestParam int uid,@RequestParam String resetCode) throws Exception;
	
	@Operation(summary = "Reset your password",tags = { "Home Api" },description = " user|admin can reset there password")
	@PostMapping("/reset-pwd")
	public ResponseEntity<?> resetPassword(@RequestBody PwdResetRequest pwdResetRequest) throws Exception;
	
}
