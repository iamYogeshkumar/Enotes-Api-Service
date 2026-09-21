package com.enotes.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.entity.Category;
import com.enotes.exception.ExistDataException;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repo.CategoryRepository;
import com.enotes.service.CategoryService;
import com.enotes.util.Validation;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private Validation validation;
	
	@Override
	public boolean saveCategory(CategoryDto categoryDto) {
		
		//validation checking 
		validation.categoryValidation(categoryDto);
		
		// check category exist or not
		boolean existByName = categoryRepo.existsByName(categoryDto.getName().trim());
		
		if(existByName) {
			// throw error
			throw new ExistDataException("Category already exist ");
		}
		
		Category category = mapper.map(categoryDto, Category.class);
		
		
		
		if(ObjectUtils.isEmpty(category.getId())) {
			category.setIsDeleted(false);
			category.setIsActive(true);
//			category.setCreatedBy(1);
//			category.setUpdatedBy(1);
			
		}else {
			UpdateCategory(category);
		}
		
		Category save = categoryRepo.save(category);
		
		return ObjectUtils.isEmpty(save)?false:true;
	}

	private void UpdateCategory(Category category) {
		Optional<Category> findById = categoryRepo.findById(category.getId());
		if(findById.isPresent()) {
			Category existCategory = findById.get();
			category.setCreatedBy(existCategory.getCreatedBy());
			category.setCreatedOn(existCategory.getCreatedOn());
			category.setIsDeleted(existCategory.getIsDeleted());
			
//			category.setUpdatedBy(existCategory.getUpdatedBy());
//			category.setUpdatedOn(new Date());
			
		}
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> Categories = categoryRepo.findByIsDeletedFalse();
		List<CategoryDto> list = Categories.stream().map(c->mapper.map(c, CategoryDto.class)).toList();
		
		return list;
	}

	@Override
	public List<CategoryResponse> getActiveCategory() {
		List<Category> Categories = categoryRepo.findByIsActiveTrueAndIsDeletedFalse();
		List<CategoryResponse> list = Categories.stream().map(active->mapper.map(active, CategoryResponse.class)).toList();
		
		return list;
	}

	@Override
	public CategoryDto getCategoryById(Integer id) throws ResourceNotFoundException {
		Category findById = categoryRepo.findByIdAndIsDeletedFalse(id).orElseThrow(()->new ResourceNotFoundException("resource not found "+id));
		
			CategoryDto categoryDto = mapper.map(findById,CategoryDto.class);
			return categoryDto;
		
	}

	@Override
	public boolean deleteCategoryById(Integer id) {
		Optional<Category> findById = categoryRepo.findById(id);
		if (findById.isPresent()) {
			Category category = findById.get();
			category.setIsDeleted(true);
			categoryRepo.save(category);
			return true;
		}
		return false;
	}

}
