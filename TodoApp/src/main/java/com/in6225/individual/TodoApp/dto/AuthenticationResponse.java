package com.in6225.individual.TodoApp.dto;

import lombok.Data;

@Data
public class AuthenticationResponse {
	private String jwtToken;
	private Integer user_id;
	private String username;
	public AuthenticationResponse(String jwt, Integer id, String userN) {
		jwtToken = jwt;
		user_id = id;
		username = userN;
	}
}
