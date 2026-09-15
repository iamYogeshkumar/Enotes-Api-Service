package com.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enotes.entity.User;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.SuccessException;
import com.enotes.repo.UserRepository;
import com.enotes.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean verifyAccount(int userId, String verificationCode) throws Exception {
		
		User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("user not found"));
		
		if(user.getAccountStatus().getVerificationCode()==null) {
			throw new SuccessException("Account is already verified");
		}
		
		if(user.getAccountStatus().getVerificationCode().equals(verificationCode)) {
			user.getAccountStatus().setActive(true);
			user.getAccountStatus().setVerificationCode(null);
			userRepository.save(user);
			
			return true;
		}
		
		return false;
	}

}
