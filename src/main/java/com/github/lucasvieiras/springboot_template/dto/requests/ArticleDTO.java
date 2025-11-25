package com.github.lucasvieiras.springboot_template.dto.requests;

import com.github.lucasvieiras.springboot_template.entities.Category;
import com.github.lucasvieiras.springboot_template.entities.Tag;

import java.util.Set;
import java.util.UUID;

public record ArticleDTO(
        UUID id,
        String title,
        String subtitle,
        Set<Tag> tags,
        Set<Category> categories,
        String contentMarkdown
) {}
