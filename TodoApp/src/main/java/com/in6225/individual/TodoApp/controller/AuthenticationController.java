package com.in6225.individual.TodoApp.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in6225.individual.TodoApp.dto.AuthenticationRequest;
import com.in6225.individual.TodoApp.dto.AuthenticationResponse;
import com.in6225.individual.TodoApp.model.User;
import com.in6225.individual.TodoApp.repos.UserRepository;
//import com.in6225.individual.TodoApp.service.UserService;
import com.in6225.individual.TodoApp.utils.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/authenticate")
	public AuthenticationResponse createdAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws BadCredentialsException,DisabledException,IOException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
			
		}catch (BadCredentialsException e) {
			throw new BadCredentialsException("Incorrect username or password.");

		}catch (DisabledException e) {
			throw new DisabledException("Login Disabled.");
		}
//		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		User user = userRepository.findFirstByUsername(authenticationRequest.getUsername());
		String jwt = jwtUtil.generateToken(authenticationRequest.getUsername());
		return new AuthenticationResponse(jwt, user.getId(), user.getUsername());

	}
	
	@GetMapping("/validateToken")
	public ResponseEntity<?> validateToken(@RequestHeader(value = "Authorization") String bearerToken) {
	    try {
	    	System.out.println("validateToken get "+bearerToken);
	        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
	            String token = bearerToken.substring(7);
	            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtUtil.extractUsername(token));
	            boolean isTokenValid = jwtUtil.validateToken(token, userDetails);
	            if (isTokenValid) {
	    	        Map<String, String> response = new HashMap<>();
	    	        response.put("message", "Token is valid");
	                return ResponseEntity.ok(response);
	            } else {
	    	    	System.out.println("Token is invalid");
	                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is not valid");
	            }
	        } else {
    	    	System.out.println("Header is invalid");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bearer token not found or improperly formatted");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token validation error: " + e.getMessage());
	    }
	}
}
