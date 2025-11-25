package com.github.lucasvieiras.springboot_template.repositories;

import com.github.lucasvieiras.springboot_template.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByValue(String value);
}
