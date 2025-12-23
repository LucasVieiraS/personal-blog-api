package com.lucasvieiras.blog_api.dtos.requests.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TagRequest(
        @NotBlank(message = "Tag value is required")
        @Size(min = 2, max = 50, message = "Tag must be between 2 and 50 characters")
        String value
) {}
