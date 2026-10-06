package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.PasswordChangeReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "User", description = "Authenticated User Operation APis")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

	@Operation(summary = "Get User Profile",tags = {"User"},description = "get Authenticated User profile")
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile( @AuthenticationPrincipal CustomUserDetails userDetails);
	
	@Operation(summary = "User Account Change Password",tags = {"User"},description = "Authenticated user can change his password")
	@PostMapping("/chng-pwd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeReq changeReq);
	
	
}
