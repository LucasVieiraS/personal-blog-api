package com.lucasvieiras.blog_api.dtos;

import java.util.UUID;

public record CategoryDTO(
        UUID id,
        String value
) {}
