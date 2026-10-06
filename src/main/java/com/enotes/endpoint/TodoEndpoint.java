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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TODO", description = "ALl todo Operation endpoint ")
@RequestMapping("/api/v1/todo")
public interface TodoEndpoint {

	@Operation(summary = "Save Todo",tags = {"TODO"},description ="save your todo" )
	@PostMapping("/")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto dto) throws Exception;
	
	@Operation(summary = "getTodoByTodoId",tags = {"TODO"},description = "Get todo by Id")
	@GetMapping("/{todoId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getTodoByTodoId(@PathVariable int todoId) throws Exception;
	
	@Operation(summary = "Get All Todo By User",tags = {"TODO"},description ="Get All Todo By User" )
	@GetMapping("/list")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllTodoByUser(int todoId) throws Exception;
	
	
	
	
}
