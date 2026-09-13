package com.enotes.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.TodoDto;
import com.enotes.dto.TodoDto.StatusDto;
import com.enotes.dto.UserDto;
import com.enotes.entity.Role;
import com.enotes.enums.TodoStatus;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.ValidationException;
import com.enotes.repo.RoleRepository;

@Component
public class Validation {

	@Autowired
	private RoleRepository roleRepository;

	public void categoryValidation(CategoryDto categoryDto) {

		Map<String, Object> error = new LinkedHashMap<>();

		if (ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category object should not be empty or null");
		} else {
			// validation name field
			if (ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is empty or null");
			} else {
				if (categoryDto.getName().length() <= 2) {
					error.put("name", "name length atleast 3");
				}
				if (categoryDto.getName().length() > 500) {
					error.put("name", "name length must be less than 500");
				}
			}
		}

		// validation description
		if (ObjectUtils.isEmpty(categoryDto.getDiscription())) {
			error.put(categoryDto.getDiscription(), "Category discription should not be empty or null");
//			throw new IllegalArgumentException("Category discription should not be empty or null");
		}

		// validation isActive
		if (ObjectUtils.isEmpty(categoryDto.getIsActive())) {
			error.put("isACtive", "isACtive  should not be empty or null");
		} else {
			// isACtive name field
			if (categoryDto.getIsActive() != Boolean.TRUE.booleanValue()
					&& categoryDto.getIsActive() != Boolean.FALSE.booleanValue()) {
				error.put("isACtive", "isACtive  should be boolean type");
			}
		}

		if (!error.isEmpty()) {
			throw new ValidationException(error);
		}

	}

	public void todoValidation(TodoDto todo) throws Exception {

		TodoStatus[] status = TodoStatus.values();

		TodoDto.StatusDto reqStatus = todo.getStatus();
//		
//		for(TodoStatus s:status) {
//			
//			if(reqStatus.getId()==s.getId()) {
//				return ;
//			}
//			
//			
//		}

		boolean anyMatch = Arrays.stream(status).anyMatch(st -> st.getId() == reqStatus.getId());
		if (anyMatch) {
			return;
		} else {
			throw new ResourceNotFoundException("invalid status");
		}

	}

	public void userValidation(UserDto userDto) {

		if (!StringUtils.hasText(userDto.getFirstName())) {
			throw new IllegalArgumentException("first name is invalid");
		}

		if (!StringUtils.hasText(userDto.getLastName())) {
			throw new IllegalArgumentException("last name is invalid");
		}

		if (!StringUtils.hasText(userDto.getEmail()) || !userDto.getEmail().matches(Constant.EMAIL_REGEX)) {
			throw new IllegalArgumentException("first name is invalid");
		}
		
		

		if (!StringUtils.hasText(userDto.getMobNo()) || !userDto.getMobNo().matches(Constant.MOBILE_REGEX)) {
			throw new IllegalArgumentException("mobile no is invalid");
		}

		if (CollectionUtils.isEmpty(userDto.getRole())) {
			throw new IllegalArgumentException("role  is invalid");
		} else {
			List<Integer> roleId = roleRepository.findAll().stream().map(Role::getId).toList();

			List<Integer> invalidReqRoleIds = userDto.getRole().stream().map(r -> r.getId())
					.filter(r -> !roleId.contains(r)).toList();
			if (!CollectionUtils.isEmpty(invalidReqRoleIds)) {
				throw new IllegalArgumentException("role  is invalid");
			}

		}

	}
}
