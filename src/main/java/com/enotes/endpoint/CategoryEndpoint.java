package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.enotes.util.Constant.ROLE_ADMIN;
import static com.enotes.util.Constant.ROLE_ADMIN_USER;
import com.enotes.dto.CategoryDto;
import com.enotes.exception.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Category Api" , description = "All Category operation api")
@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {
	
	@Operation(summary = "saveCategory ",tags = { "Category Api" },description = " admin can create category")
	@PostMapping("/save-category")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);
	
	@Operation(summary = "get All category ",tags = { "Category Api" },description = " admin will get all category")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllcategory();
	
	@Operation(summary = "get All Active category ",tags = { "Category Api" },description = " admin/user will get all the active category")
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getAllActivecategory();
	
	@Operation(summary = "get Category Details By category Id ",tags = { "Category Api" },description = " admin will getting the category details by category Id")
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws ResourceNotFoundException;
	
	@Operation(summary = "delete CategoryDetails ById ",tags = { "Category Api" }, description = " admin can delete category by category Id")
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id);
	
	
	

}
