package com.lucasvieiras.blog_api.dto.requests.article;

import jakarta.annotation.Nullable;

public record ArticleRequest(
        @Nullable String title
) {}
