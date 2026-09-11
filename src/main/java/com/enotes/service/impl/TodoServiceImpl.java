package com.enotes.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.enotes.dto.TodoDto;
import com.enotes.entity.Todo;
import com.enotes.enums.TodoStatus;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repo.TodoRepository;
import com.enotes.service.TodoService;
import com.enotes.util.Validation;
@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private Validation validation;
	
	
	@Override
	public Boolean saveTodo(TodoDto todoDto) throws Exception {
		// validate todo status
		validation.todoValidation(todoDto);
		
		Todo todo = mapper.map(todoDto, Todo.class);
		todo.setStatusId(todoDto.getStatus().getId());
		System.out.println(todo.getStatusId());
		Todo saveTodo = todoRepository.save(todo);
		if(ObjectUtils.isEmpty(saveTodo)) {
			return false;
		}
		
		return true;
	}

	@Override
	public TodoDto getTodoById(int todoId) throws Exception {
		
		Todo todoEntity = todoRepository.findById(todoId).orElseThrow(()-> new ResourceNotFoundException(" todo ID does not exist"));
		TodoDto todoDto = mapper.map(todoEntity, TodoDto.class);
		TodoDto.StatusDto statusDto = TodoDto.StatusDto.builder().id(todoDto.getStatus().getId()).name(TodoStatus.getStatusName(todoDto.getStatus().getId())).build();
				
		todoDto.setStatus(statusDto);
		return todoDto;
	}

	@Override
	public List<TodoDto> getTodoByUser() {
		int userId=1;
		 List<Todo>	list=todoRepository.findByCreatedBy( userId);
		return list.stream().map(todo->mapper.map(list, TodoDto.class)).toList();
	}

}
