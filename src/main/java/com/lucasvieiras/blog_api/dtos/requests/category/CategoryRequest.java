package com.lucasvieiras.blog_api.dtos.requests.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotBlank(message = "Category is required")
        @Size(min = 2, max = 50, message = "Category must be between 2 and 50 characters")
        String value
) {}
