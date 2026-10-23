package com.enotes.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enotes.dto.CategoryDto;
import com.enotes.entity.Category;
import com.enotes.service.CategoryService;

@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

	@Mock
	private CategoryService categoryService;

	@InjectMocks
	private CategoryController controller;

	private CategoryDto categoryDto = null;

	private Category category = null;

	private List<Category> categories = new ArrayList<>();
	private List<CategoryDto> categoriesDto = new ArrayList<>();
	
	
	@BeforeEach
	public void initialize() {
		categoryDto = CategoryDto.builder().id(null).name("Java Notes").discription("Java Notes").isActive(true)
				.build();

		category=new Category();
		category.setId(categoryDto.getId());
		category.setName(categoryDto.getName());
		category.setDiscription(categoryDto.getDiscription());
		category.setIsActive(categoryDto.getIsActive());
		
		categories.add(category);
		categories.add(category);
		
		categoriesDto.add(categoryDto);
		categoriesDto.add(categoryDto);
		
	}
	
	@Test
	public void testSaveCategory() {
		when(categoryService.saveCategory(categoryDto)).thenReturn(true);
		
		ResponseEntity<?> response = controller.saveCategory(categoryDto);
		
		Object body = response.getBody();
		
		Map<String,String> json=(Map<String,String>)body;
		
		Assertions.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
		
		Assertions.assertEquals(json.get("msg"), "Saved scucess");
		
		
	}
	
	@Test
	public void testCategoryNotSaved() {
		when(categoryService.saveCategory(categoryDto)).thenReturn(false);
		
		ResponseEntity<?> response = controller.saveCategory(categoryDto);
		
		Object body = response.getBody();
		
        Map<String,String> json=(Map<String,String>)body;
        
        Assertions.assertEquals(json.get("msg"), "not saved");
        
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        
	}
	
}
