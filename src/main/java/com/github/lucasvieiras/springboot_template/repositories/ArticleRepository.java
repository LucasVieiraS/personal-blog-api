package com.github.lucasvieiras.springboot_template.repositories;

import com.github.lucasvieiras.springboot_template.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ArticleRepository extends JpaRepository<Article, UUID> {
    Optional<Article> findByTitle(String title);
}
