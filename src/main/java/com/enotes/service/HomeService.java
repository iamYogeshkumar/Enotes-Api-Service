package com.enotes.service;

public interface HomeService {

	public boolean verifyAccount(int userId,String verificationCode) throws Exception;
	
}
