package com.github.lucasvieiras.springboot_template.repositories;

import com.github.lucasvieiras.springboot_template.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    List<User> findByActiveTrue();
    
    List<User> findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);
    
    boolean existsByEmail(String email);
}
