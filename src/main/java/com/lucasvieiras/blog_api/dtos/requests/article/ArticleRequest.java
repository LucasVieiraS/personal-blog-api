package com.lucasvieiras.blog_api.dtos.requests.article;

import jakarta.annotation.Nullable;

public record ArticleRequest(
        @Nullable String title
) {}
