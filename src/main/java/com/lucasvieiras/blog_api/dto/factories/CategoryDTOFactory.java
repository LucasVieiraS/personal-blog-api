package com.lucasvieiras.blog_api.dto.factories;

import com.lucasvieiras.blog_api.dto.CategoryDTO;
import com.lucasvieiras.blog_api.entities.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDTOFactory {
    public CategoryDTO create(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getValue()
        );
    }

    public List<CategoryDTO> create(List<Category> categories) {
        return categories.stream().map(this::create).toList();
    }
}
