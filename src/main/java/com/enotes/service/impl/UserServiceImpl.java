package com.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

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
	
	@Override
	public boolean register(UserDto dto) {
		//validation
		validation.userValidation(dto);
		
		User user = mapper.map(dto, User.class);
		setRole(dto,user);
		
		User saveUser = userRepository.save(user);
		
		if(ObjectUtils.isEmpty(saveUser)) {
			return false;
		}
		
		return true;
	}
	
	private void setRole(UserDto userDto, User user) {
		
		List<Integer> roleId = userDto.getRole().stream().map(r->r.getId()).toList();
		
		List<Role> userRoles = roleRepository.findAllById(roleId);
		user.setRole(userRoles);
	}

}
