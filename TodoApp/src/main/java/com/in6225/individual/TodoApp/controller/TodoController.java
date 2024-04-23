package com.in6225.individual.TodoApp.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.in6225.individual.TodoApp.dto.TodoDTO;
import com.in6225.individual.TodoApp.model.Todo;
import com.in6225.individual.TodoApp.model.User;
import com.in6225.individual.TodoApp.repos.UserRepository;
import com.in6225.individual.TodoApp.service.TodoService;
import com.in6225.individual.TodoApp.utils.JwtUtil;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    private TodoService todoService;
    
    @Autowired
    private JwtUtil jwtUtil;
	@Autowired
	private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<TodoDTO> createTodo(@RequestBody Todo todo, @RequestHeader("Authorization") String authorizationHeader) {;
		String token = authorizationHeader.substring(7); 
		String username = jwtUtil.extractUsername(token);
		User user = userRepository.findFirstByUsername(username);
		todo.setUser(user);
        Todo createdTodo = todoService.createTodo(todo);
        TodoDTO createdTodoDTO = todoService.createTodoDTO(todo);
    	System.out.println("222");
        return new ResponseEntity<>(createdTodoDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDTO> getTodoById(@PathVariable Long id) {
        java.util.Optional<Todo> todo = todoService.getTodoById(id);
        return todo.map(value -> new ResponseEntity<>(todoService.createTodoDTO(value), HttpStatus.OK))
                   .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TodoDTO>> getTodosByUserId(@PathVariable Long userId) {
    	List<Todo> todos = todoService.getAllTodosByUserId(userId);
        List<TodoDTO> todosDto = todos.stream()
                .map(todoService::createTodoDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(todosDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        try {
            Todo updatedTodo = todoService.updateTodo(id, todo);
            TodoDTO updatedTodoDTO = todoService.createTodoDTO(updatedTodo);
            return new ResponseEntity<>(updatedTodoDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
