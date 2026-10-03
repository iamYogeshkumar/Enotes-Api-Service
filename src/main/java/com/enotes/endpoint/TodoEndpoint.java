package com.enotes.endpoint;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.enotes.util.Constant.ROLE_USER;
import com.enotes.dto.TodoDto;

@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto dto) throws Exception;
	
	
	@GetMapping("/{todoId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByTodoId(@PathVariable int todoId) throws Exception;
	
	@GetMapping("/list")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodoByUser(int todoId) throws Exception;
	
	
	
	
}
