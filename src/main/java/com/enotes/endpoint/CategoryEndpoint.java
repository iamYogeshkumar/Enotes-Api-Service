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

@RequestMapping("/api/v1/category")
public interface CategoryEndpoint {
	
	@PostMapping("/save-category")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto);
	
	
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllcategory();
	
	@GetMapping("/active")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> getAllActivecategory();
	
	@GetMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws ResourceNotFoundException;
	
	@DeleteMapping("/{id}")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> deleteCategoryDetailsById(@PathVariable Integer id);
	
	
	

}
