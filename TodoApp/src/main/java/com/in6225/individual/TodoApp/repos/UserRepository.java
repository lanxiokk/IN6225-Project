package com.in6225.individual.TodoApp.repos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.in6225.individual.TodoApp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findFirstByUsername(String username);
}
