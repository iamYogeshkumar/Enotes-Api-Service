package com.enotes.service;

import java.util.List;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;

public interface CategoryService {

	boolean saveCategory(CategoryDto category);
	
	List<CategoryDto> getAllCategory();
	
	public List<CategoryResponse> getActiveCategory();

	CategoryDto getCategoryById(Integer id);

	boolean deleteCategoryById(Integer id);

	
}
