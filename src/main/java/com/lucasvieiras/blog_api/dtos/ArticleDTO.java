package com.lucasvieiras.blog_api.dtos;

import com.lucasvieiras.blog_api.entities.Category;
import com.lucasvieiras.blog_api.entities.Tag;
import com.lucasvieiras.blog_api.entities.User;

import java.util.Set;
import java.util.UUID;

public record ArticleDTO(
        UUID id,
        String title,
        String subtitle,
        Set<Tag> tags,
        Set<Category> categories,
        String contentMarkdown,
        User authorId
) {}
