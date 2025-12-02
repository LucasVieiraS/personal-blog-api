package com.lucasvieiras.blog_api.dto;

import java.util.UUID;

public record TagDTO(
        UUID id,
        String value
) {}


