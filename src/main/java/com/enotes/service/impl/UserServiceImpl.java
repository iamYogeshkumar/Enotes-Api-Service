package com.enotes.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.EmailRequest;
import com.enotes.dto.PasswordChangeReq;
import com.enotes.dto.PwdResetRequest;
import com.enotes.entity.User;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repo.UserRepository;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void changePassword(PasswordChangeReq changeReq) {
		 User loggedInUser = CommonUtil.getLoggedInUser();
		
		 if(!passwordEncoder.matches( changeReq.getOldPassword(),loggedInUser.getPassword())) {
			 throw new IllegalArgumentException("Your old password is incorrect");
		 }
		
		 loggedInUser.setPassword(passwordEncoder.encode(changeReq.getNewPassword()));
		
		 userRepository.save(loggedInUser);
		
	}

	@Override
	public void sendEmailPwdReset(String email,HttpServletRequest servletRequest) throws Exception {
		User user = userRepository.findByEmail(email);
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("invalid email");
		}
		sendEmailResetLink(user,servletRequest);
		
	}

	private void sendEmailResetLink(User user,HttpServletRequest servletRequest) throws Exception {
		
		user.getAccountStatus().setPasswordResetToken(UUID.randomUUID().toString());
		userRepository.save(user);
		
		String message = "Hi  [[username]]"  + ",<br><br>"
		        + "Your have requested to reset your passwordy.<br><br>"
		        + "<h5>Click the link below to verify your account:</h5>"
		        + "<a href='[[url]]'>Click here</a><br><br>"
		        + "Thanks,<br>"
		        + "Enotes.com";
		
		String url = CommonUtil.getUrl(servletRequest);
		
		message=message.replace("[[username]]", user.getFirstName());

		message=message.replace("[[url]]",url+"/api/v1/home/verify-pwd-link?uid="+user.getId()+"&&code="+user.getAccountStatus().getPasswordResetToken());
		
		EmailRequest emailRequest = EmailRequest.builder()
		                             .to(user.getEmail())
		                             .title("Password Reset ")
		                             .subject("request to reset the password")
		                             .message(message)
		                             .build();
		
		//send email to reset the pwd
		emailService.send(emailRequest);
		
	}

	@Override
	public void verifyPasswordResetLink(int uid, String resetCode) throws Exception {
		User user = userRepository.findById(uid)
				.orElseThrow(()-> new ResourceNotFoundException("invalid user id or user not found"));
		
		if(user.getAccountStatus().getPasswordResetToken()==null) {
			throw new IllegalArgumentException("password reset link already used o expire");
		}
		
		if(!user.getAccountStatus().getPasswordResetToken().equals(resetCode)) {
			throw new IllegalArgumentException("invalid url");
		}
		
		if(user.getAccountStatus().getPasswordResetToken().equals(resetCode)) {
			user.getAccountStatus().setPasswordResetToken(null);
			userRepository.save(user);
		}
		
		
	}

	@Override
	public void resetPwd(PwdResetRequest pwdResetRequest) throws Exception {
		User user = userRepository.findById(pwdResetRequest.getUid())
				.orElseThrow(()-> new ResourceNotFoundException("invalid uid"));
		user.setPassword(passwordEncoder.encode(pwdResetRequest.getNewPassword()));
		userRepository.save(user);
	}

}
