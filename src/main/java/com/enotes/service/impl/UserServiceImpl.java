package com.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.EmailRequest;
import com.enotes.dto.UserDto;
import com.enotes.entity.Role;
import com.enotes.entity.User;
import com.enotes.repo.RoleRepository;
import com.enotes.repo.UserRepository;
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
	
	@Override
	public boolean register(UserDto dto) throws Exception {
		//validation
		validation.userValidation(dto);
		
		User user = mapper.map(dto, User.class);
		setRole(dto,user);
		
		User saveUser = userRepository.save(user);
		
		if(ObjectUtils.isEmpty(saveUser)) {
			
			return false; //registration fail
		}
		
		
		emailSend(saveUser);
		
		return true;
	}
	
	private void emailSend(User saveUser) throws Exception {
		String message = "Hi " + saveUser.getFirstName() + ",<br><br>"
		        + "Your account has been registered successfully.<br><br>"
		        + "<h5>Click the link below to verify your account:</h5>"
		        + "<a href='YOUR_VERIFICATION_URL'>Click here</a><br><br>"
		        + "Thanks,<br>"
		        + "Enotes.com";

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

}
