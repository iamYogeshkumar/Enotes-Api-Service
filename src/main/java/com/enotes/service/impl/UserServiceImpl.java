package com.enotes.service.impl;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.dto.EmailRequest;
import com.enotes.dto.LoginRequest;
import com.enotes.dto.LoginResponse;
import com.enotes.dto.UserDto;
import com.enotes.entity.AccountStatus;
import com.enotes.entity.Role;
import com.enotes.entity.User;
import com.enotes.repo.RoleRepository;
import com.enotes.repo.UserRepository;
import com.enotes.service.JwtService;
import com.enotes.service.UserService;
import com.enotes.util.Validation;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Autowired
	private JwtService jwtService;
	
	
	@Override
	public boolean register(UserDto dto ,String url) throws Exception {
		//validation
		validation.userValidation(dto);
		
		dto.setPassword(encoder.encode(dto.getPassword()));
		
		User user = mapper.map(dto, User.class);
		setRole(dto,user);
		
		AccountStatus accountStatus = AccountStatus.builder()
		.isActive(false)
		.verificationCode(UUID.randomUUID().toString())
		.build();
		
		user.setAccountStatus(accountStatus);
				
		
		User saveUser = userRepository.save(user);
		
		if(ObjectUtils.isEmpty(saveUser)) {
			
			return false; //registration fail
		}
		
		
		emailSend(saveUser,url);
		// registration success
		return true;
	}
	
	private void emailSend(User saveUser,String url) throws Exception {
		String message = "Hi  [[username]]"  + ",<br><br>"
		        + "Your account has been registered successfully.<br><br>"
		        + "<h5>Click the link below to verify your account:</h5>"
		        + "<a href='[[url]]'>Click here</a><br><br>"
		        + "Thanks,<br>"
		        + "Enotes.com";
		
		message=message.replace("[[username]]", saveUser.getFirstName());

		message=message.replace("[[url]]",url+"/api/v1/home/verify?uid="+saveUser.getId()+"&&code="+saveUser.getAccountStatus().getVerificationCode());
		
		EmailRequest emailRequest = EmailRequest.builder()
		                             .to(saveUser.getEmail())
		                             .title("Account creating confirmation")
		                             .subject("Account created Success")
		                             .message(message)
		                             .build();
		
		emailService.send(emailRequest);
		
	}

	private void setRole(UserDto userDto, User user) {
		
		List<Integer> roleId = userDto.getRole().stream().map(r->r.getId()).toList();
		
		List<Role> userRoles = roleRepository.findAllById(roleId);
		user.setRole(userRoles);
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		String token = "jskjkndfmdsjjsdhfhhvjbvhvdjfvvjfvkjdjvkjffjd";
		
		Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
	
		if(authenticate.isAuthenticated()) {
			
			CustomUserDetails customUserDetails=(CustomUserDetails)authenticate.getPrincipal();
			User user = customUserDetails.getUser();
			
			String jwtToken = jwtService.generateJwtToken(user);
			
			LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).userDto(mapper.map(user, UserDto.class)).build();
			
			return loginResponse;
			
		}
		
		return null;
		
	}

}
