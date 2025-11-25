package com.github.lucasvieiras.springboot_template.services;

import com.github.lucasvieiras.springboot_template.dto.requests.category.CategoryRequest;
import com.github.lucasvieiras.springboot_template.entities.Category;
import com.github.lucasvieiras.springboot_template.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .value(request.value())
                .build();
        return categoryRepository.save(category);
    }

    public Category updateCategory(CategoryRequest request, UUID id) {
        Category Category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (request.value() != null) Category.setValue(request.value());

        return categoryRepository.save(Category);
    }

    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category findCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Category findCategoryByTitle(String title) {
        return categoryRepository.findByValue(title).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        categoryRepository.delete(category);
    }
}
