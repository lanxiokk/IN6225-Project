package com.in6225.individual.TodoApp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.in6225.individual.TodoApp.dto.SignupDTO;
import com.in6225.individual.TodoApp.dto.UserDTO;
import com.in6225.individual.TodoApp.model.User;
import com.in6225.individual.TodoApp.repos.UserRepository;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	public UserDTO createUser(SignupDTO signupDTO) {
		User user = new User();
		user.setUsername(signupDTO.getUsername());
		user.setPassword(new BCryptPasswordEncoder().encode(signupDTO.getPassword()));
		User createdUser = userRepository.save(user);
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(createdUser.getId());
		userDTO.setUsername(createdUser.getUsername());

		return userDTO;
	}

	public boolean hasUserWithUsername(String username) {
		return userRepository.findFirstByUsername(username) != null;
	}



}
