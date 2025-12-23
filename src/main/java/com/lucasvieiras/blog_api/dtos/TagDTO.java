package com.lucasvieiras.blog_api.dtos;

import java.util.UUID;

public record TagDTO(
        UUID id,
        String value
) {}


