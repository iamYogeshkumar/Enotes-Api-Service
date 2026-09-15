package com.enotes.service;

import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserDto;

public interface UserService {

	boolean register(UserDto dto,String url) throws Exception;

	LoginResponse login(LoginRequest loginRequest);
	
	
}
