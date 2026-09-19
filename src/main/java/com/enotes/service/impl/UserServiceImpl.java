package com.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enotes.dto.PasswordChangeReq;
import com.enotes.entity.User;
import com.enotes.repo.UserRepository;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void changePassword(PasswordChangeReq changeReq) {
		 User loggedInUser = CommonUtil.getLoggedInUser();
		
		 if(!passwordEncoder.matches( changeReq.getOldPassword(),loggedInUser.getPassword())) {
			 throw new IllegalArgumentException("Your old password is incorrect");
		 }
		
		 loggedInUser.setPassword(passwordEncoder.encode(changeReq.getNewPassword()));
		
		 userRepository.save(loggedInUser);
		
	}

}
