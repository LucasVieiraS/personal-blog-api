package com.github.lucasvieiras.springboot_template.dto.requests.article;

import com.github.lucasvieiras.springboot_template.entities.Category;
import com.github.lucasvieiras.springboot_template.entities.Tag;

import java.util.Set;

public record CreateArticleRequest(
        String title,
        String subtitle,
        Set<Tag> tags,
        Set<Category> categories,
        String contentMarkdown
) {


}
