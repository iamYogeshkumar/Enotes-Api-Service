package com.enotes.service;

import com.enotes.entity.User;

public interface JwtService {

	String generateJwtToken(User user);
}
