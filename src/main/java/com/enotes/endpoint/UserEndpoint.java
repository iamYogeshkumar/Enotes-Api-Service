package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.PasswordChangeReq;

@RequestMapping("/api/v1/user")
public interface UserEndpoint {

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile( @AuthenticationPrincipal CustomUserDetails userDetails);
	
	@PostMapping("/chng-pwd")
	public ResponseEntity<?> changePassword(@RequestBody PasswordChangeReq changeReq);
	
	
}
