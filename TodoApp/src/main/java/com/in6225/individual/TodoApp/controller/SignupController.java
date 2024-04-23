package com.in6225.individual.TodoApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.in6225.individual.TodoApp.dto.SignupDTO;
import com.in6225.individual.TodoApp.dto.UserDTO;
import com.in6225.individual.TodoApp.service.UserService;

@RestController
public class SignupController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> signupUser(@RequestBody SignupDTO signupDTO){
		System.out.println("!!!!!!!");
		System.out.println(signupDTO.getUsername());
		System.out.println(signupDTO.getPassword());


		if(userService.hasUserWithUsername(signupDTO.getUsername())) {
			return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
		}
		
		UserDTO createdUser = userService.createUser(signupDTO);
		if(createdUser == null) {
			return new ResponseEntity<>("User not created, try later!", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}
}
