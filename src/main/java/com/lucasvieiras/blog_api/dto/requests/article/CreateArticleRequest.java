package com.lucasvieiras.blog_api.dto.requests.article;

import com.lucasvieiras.blog_api.entities.Category;
import com.lucasvieiras.blog_api.entities.Tag;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record CreateArticleRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
        String title,

        @Size(min = 5, max = 100, message = "Subtitle must be between 5 and 100 characters")
        @Nullable
        String subtitle,

        Set<Tag> tags,
        Set<Category> categories,

        @NotBlank(message = "Content markdown is required.")
        @Min(value = 5, message = "Content markdown length must be greater than 5")
        String contentMarkdown
) {


}
