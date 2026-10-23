package com.enotes.service;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.enotes.dto.CategoryDto;
import com.enotes.entity.Category;
import com.enotes.exception.ExistDataException;
import com.enotes.repo.CategoryRepository;
import com.enotes.service.impl.CategoryServiceImpl;
import com.enotes.util.Validation;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

	@Mock
	private CategoryRepository categoryRepository;

	@InjectMocks
	private CategoryServiceImpl categoryService;

	private CategoryDto categoryDto = null;
	
	private Category category = null;
	
	private List<Category> categories=new ArrayList<>();
	private List<CategoryDto> categoriesDto=new ArrayList<>();

	@Mock
	private ModelMapper mapper;

	@Mock
	private Validation validation;

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
		// arrange
		when(categoryRepository.existsByName(categoryDto.getName().trim())).thenReturn(false);
		when(mapper.map(categoryDto, Category.class)).thenReturn(category);
		when(categoryRepository.save(category)).thenReturn(category);

		// act
		boolean saveCategory = categoryService.saveCategory(categoryDto);

		// assert
		Assertions.assertTrue(saveCategory);

		// verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepository).existsByName(categoryDto.getName());
		verify(categoryRepository).save(category);
	}
	
	@Test
	public void testCategoryExist() {
		when(categoryRepository.existsByName(categoryDto.getName())).thenReturn(true);
		ExistDataException exception = Assertions.assertThrows(ExistDataException.class, ()->categoryService.saveCategory(categoryDto));
		
		Assertions.assertEquals("Category already exist ", exception.getMessage());
		
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepository).existsByName(categoryDto.getName());
		verify(categoryRepository,never()).save(category);
		
	}
	
	@Test
	public void testUpdateCategory() {
		
		categoryDto.setId(1);
		category.setId(1);
		
		// arrange
		when(categoryRepository.existsByName(categoryDto.getName().trim())).thenReturn(false);
		when(mapper.map(categoryDto, Category.class)).thenReturn(category);
		when(categoryRepository.save(category)).thenReturn(category);

		// act
		boolean saveCategory = categoryService.saveCategory(categoryDto);

		// assert
		Assertions.assertTrue(saveCategory);

		// verify
		verify(validation).categoryValidation(categoryDto);
		verify(categoryRepository).existsByName(categoryDto.getName());
		verify(categoryRepository).save(category);
	}
	
	@Test
	public void testGetAllCategory() {
			
		
		when(categoryRepository.findByIsDeletedFalse()).thenReturn(categories);
        List<CategoryDto> allCategory = categoryService.getAllCategory();		
		Assertions.assertEquals(allCategory.size(),2);
		
		verify(categoryRepository).findByIsDeletedFalse();
		
		

	}
	
}
