package com.lucasvieiras.blog_api.services;

import com.lucasvieiras.blog_api.dtos.requests.category.CategoryRequest;
import com.lucasvieiras.blog_api.entities.Category;
import com.lucasvieiras.blog_api.exceptions.BadRequestException;
import com.lucasvieiras.blog_api.exceptions.ConflictException;
import com.lucasvieiras.blog_api.exceptions.ResourceNotFoundException;
import com.lucasvieiras.blog_api.repositories.CategoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory(CategoryRequest request) {
        if (categoryRepository.findByValue(request.value()).isPresent()) {
            throw new ConflictException("Category already exists");
        }

        Category category = Category.builder()
                .value(request.value())
                .build();
        return categoryRepository.save(category);
    }

    @Transactional
    public Category updateCategory(CategoryRequest request, UUID id) {
        Category Category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (request.value() != null) Category.setValue(request.value());

        return categoryRepository.save(Category);
    }

    @Transactional
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        if (!category.getArticles().isEmpty()) {
            throw new BadRequestException("Cannot delete category that has articles assigned");
        }

        categoryRepository.delete(category);
    }

    @Transactional(readOnly = true)
    public Page<Category> findAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Category findCategoryById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Category findCategoryByTitle(String title) {
        return categoryRepository.findByValue(title).orElseThrow(() -> new ResourceNotFoundException("Category not found with title: " + title));
    }
}
