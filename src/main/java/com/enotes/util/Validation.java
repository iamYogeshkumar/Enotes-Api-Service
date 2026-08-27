package com.enotes.util;

import java.util.LinkedHashMap;
import java.util.Map;


import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.exception.ValidationException;

@Component
public class Validation {

	public void categoryValidation(CategoryDto categoryDto) {
		
		Map<String,Object> error=new LinkedHashMap<>();
		
		if(ObjectUtils.isEmpty(categoryDto)) {
			throw new IllegalArgumentException("Category object should not be empty or null");
		}
		else {
			//validation name field
			if(ObjectUtils.isEmpty(categoryDto.getName())) {
				error.put("name", "name field is empty or null");
			}else {
				if(categoryDto.getName().length()<=2) {
					error.put("name", "name length atleast 3");
				}
				if(categoryDto.getName().length()>500) {
					error.put("name", "name length must be less than 500");
				}
			}
		}
		
		//validation description
		if(ObjectUtils.isEmpty(categoryDto.getDiscription())) {
			error.put(categoryDto.getDiscription(), "Category discription should not be empty or null");
//			throw new IllegalArgumentException("Category discription should not be empty or null");
		}
		
		//validation isActive
		if(ObjectUtils.isEmpty(categoryDto.getIsActive())) {
			error.put("isACtive", "isACtive  should not be empty or null");
		}
		else {
			//isACtive name field
				if(categoryDto.getIsActive()!= Boolean.TRUE.booleanValue() && categoryDto.getIsActive()!=Boolean.FALSE.booleanValue()) {
					error.put("isACtive", "isACtive  should be boolean type");
				}
		}
		
		if(!error.isEmpty()) {
			throw new ValidationException(error);
		}
		
	}
}
