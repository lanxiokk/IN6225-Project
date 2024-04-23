package com.in6225.individual.TodoApp.dto;

import jakarta.persistence.Column;
import lombok.Data;
@Data
public class SignupDTO {
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    
}
