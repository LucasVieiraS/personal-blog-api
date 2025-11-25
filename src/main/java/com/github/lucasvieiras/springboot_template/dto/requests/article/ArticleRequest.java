package com.github.lucasvieiras.springboot_template.dto.requests.article;

import jakarta.annotation.Nullable;

public record ArticleRequest(
        @Nullable String title
) {}
