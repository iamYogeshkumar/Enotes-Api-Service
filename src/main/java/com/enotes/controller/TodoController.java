package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.TodoDto;
import com.enotes.service.TodoService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;

	@PostMapping("/")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto dto) throws Exception {
		Boolean todoStatus = todoService.saveTodo(dto);

		if (todoStatus) {
			return CommonUtil.createBuildResponseMessage("todo created ", HttpStatus.CREATED);
		}

		return CommonUtil.createErrorResponseMessage("failed to create Todo ", HttpStatus.INTERNAL_SERVER_ERROR);

	}

	@GetMapping("/{todoId}")
	public ResponseEntity<?> getTodoByTodoId(@PathVariable int todoId) throws Exception {
		TodoDto todoDto = todoService.getTodoById(todoId);

		return CommonUtil.createBuildResponse(todoDto, HttpStatus.OK);
	}

	
	@GetMapping("/list")
	public ResponseEntity<?> getAllTodoByUser(int todoId) throws Exception {
		List<TodoDto> listOfTodo = todoService.getTodoByUser();
		if (CollectionUtils.isEmpty(listOfTodo)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createBuildResponse(listOfTodo, HttpStatus.OK);
	}

}
