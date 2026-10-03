package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.endpoint.CategoryEndpoint;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.service.CategoryService;
import com.enotes.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CategoryController implements CategoryEndpoint {

	@Autowired
	private CategoryService categoryService;

	
	@Override
	public ResponseEntity<?> saveCategory( CategoryDto categoryDto) {
		boolean saveCategory = categoryService.saveCategory(categoryDto);
		if (saveCategory) {
			return CommonUtil.createBuildResponseMessage("Saved scucess", HttpStatus.OK);
//			return new ResponseEntity<>("saved", HttpStatus.OK);
		} else {
		   return	CommonUtil.createErrorResponseMessage("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>("not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public ResponseEntity<?> getAllcategory(){
		List<CategoryDto> allCategory = categoryService.getAllCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
//			return new ResponseEntity<>("no content",HttpStatus.OK);
		}else {
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	
	
	@Override
	public ResponseEntity<?> getAllActivecategory(){
		List<CategoryResponse> allCategory = categoryService.getActiveCategory();
		if(CollectionUtils.isEmpty(allCategory)) {
//			return new ResponseEntity<>("no content",HttpStatus.OK);
			return ResponseEntity.noContent().build();
		}else {
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);
			return CommonUtil.createBuildResponse(allCategory, HttpStatus.OK);
		}
	}
	
	@Override
	public ResponseEntity<?> getCategoryDetailsById( Integer id) throws ResourceNotFoundException{
		CategoryDto categoryDto;
		categoryDto = categoryService.getCategoryById(id);
//		return new ResponseEntity<>(categoryDto,HttpStatus.OK);
		return CommonUtil.createBuildResponse(categoryDto, HttpStatus.OK);
//		try {
//			
//			categoryDto = categoryService.getCategoryById(id);
//			return new ResponseEntity<>(categoryDto,HttpStatus.OK);
//			
//			
//		} catch (ResourceNotFoundException e) {
//			log.error("controller :: getCategorybyId "+e.getMessage());
//			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//			
//		} catch (Exception e) {
//			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
//			
//		}
		
		
	}
	
	
	@Override
	public ResponseEntity<?> deleteCategoryDetailsById( Integer id){
		boolean status = categoryService.deleteCategoryById(id);
		if(!status) {
//			return new ResponseEntity<>("category not found by Id= "+id,HttpStatus.NOT_FOUND);
			return CommonUtil.createErrorResponseMessage("category not found by Id= "+id,HttpStatus.NOT_FOUND);
		}
	    return	CommonUtil.createErrorResponseMessage("Deleted", HttpStatus.OK);
//		return new ResponseEntity<>("deleted ",HttpStatus.OK);
	}

}
