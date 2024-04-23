package com.in6225.individual.TodoApp.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.in6225.individual.TodoApp.model.Todo;
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> { 
	List<Todo> findByUserId(Long userId);
}
