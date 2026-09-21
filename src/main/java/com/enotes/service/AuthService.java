package com.enotes.service;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserRequest;

public interface AuthService {

	boolean register(UserRequest dto,String url) throws Exception;

	LoginResponse login(LoginRequest loginRequest);
	
	
}
