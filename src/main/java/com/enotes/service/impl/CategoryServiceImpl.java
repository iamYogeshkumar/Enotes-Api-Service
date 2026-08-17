package com.enotes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.entity.Category;
import com.enotes.repo.CategoryRepository;
import com.enotes.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	public boolean saveCategory(Category category) {
		category.setIsDeleted(false);
		Category save = categoryRepo.save(category);
		return ObjectUtils.isEmpty(save)?true:false;
	}

	@Override
	public List<Category> getAllCategory() {
		List<Category> Categories = categoryRepo.findAll();
		return Categories;
	}

}
