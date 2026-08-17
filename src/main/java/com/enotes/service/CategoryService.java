package com.enotes.service;

import java.util.List;

import com.enotes.entity.Category;

public interface CategoryService {

	boolean saveCategory(Category category);
	
	List<Category> getAllCategory();
}
