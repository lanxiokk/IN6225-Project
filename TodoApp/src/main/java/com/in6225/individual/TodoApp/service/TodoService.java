package com.in6225.individual.TodoApp.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.in6225.individual.TodoApp.dto.TodoDTO;
import com.in6225.individual.TodoApp.dto.UserDTO;
import com.in6225.individual.TodoApp.model.Todo;
import com.in6225.individual.TodoApp.model.User;
import com.in6225.individual.TodoApp.repos.TodoRepository;
import com.in6225.individual.TodoApp.repos.UserRepository;

@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Todo createTodo(Todo todo) {
    	todo.setCreationTime(new Date());
    	todo.setModificationTime(new Date());
        return todoRepository.save(todo);
    }

    public Optional<Todo> getTodoById(Long id) {
        return todoRepository.findById(id);
    }

    public List<Todo> getAllTodosByUserId(Long userId) {
        return todoRepository.findByUserId(userId);
    }

    public Todo updateTodo(Long id, Todo todo) throws RuntimeException {
    	Date java_d = new Date();
    	Optional<Todo> existing = todoRepository.findById(id);
    	if(existing.isPresent()) {
    		Todo existing_todo = existing.get();
    		existing_todo.setId(todo.getId());
//    		existing_todo.setStatus(todo.getStatus());
    		existing_todo.setSta(todo.getSta());
    		existing_todo.setEventTime(todo.getEventTime());
    		existing_todo.setContent(todo.getContent());
    		existing_todo.setTitle(todo.getTitle());
            return todoRepository.save(existing_todo);
    	}else {
    		new RuntimeException("Todo not found!");
    	}
		return null;
    }
    

    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);
    }
    
    public TodoDTO createTodoDTO(Todo todo) {
    	TodoDTO todoDTO = new TodoDTO();
    	todoDTO.setId(todo.getId());
    	todoDTO.setUser_id(todo.getUser().getId());
//    	todoDTO.setStatus(todo.getStatus());
    	todoDTO.setSta(todo.getSta());
    	todoDTO.setCreation_time(todo.getCreationTime());
    	todoDTO.setEvent_time(todo.getEventTime());
    	todoDTO.setModification_time(todo.getModificationTime());
    	
    	todoDTO.setContent(todo.getContent());
    	todoDTO.setTitle(todo.getTitle());

		return todoDTO;

    }
}



