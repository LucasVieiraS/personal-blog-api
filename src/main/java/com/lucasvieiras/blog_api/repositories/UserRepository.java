package com.lucasvieiras.blog_api.repositories;

import com.lucasvieiras.blog_api.entities.User;
import com.lucasvieiras.blog_api.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String name);
    boolean existsByUsername(String name);
    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
}
