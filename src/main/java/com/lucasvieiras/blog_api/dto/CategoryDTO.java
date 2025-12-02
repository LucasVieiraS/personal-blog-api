package com.lucasvieiras.blog_api.dto;

import java.util.UUID;

public record CategoryDTO(
        UUID id,
        String value
) {}
