package com.github.lucasvieiras.springboot_template.repositories;

import com.github.lucasvieiras.springboot_template.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {
    Optional<Tag> findByValue(String value);
}
