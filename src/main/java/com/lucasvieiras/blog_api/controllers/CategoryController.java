package com.lucasvieiras.blog_api.controllers;

import com.lucasvieiras.blog_api.dto.factories.CategoryDTOFactory;
import com.lucasvieiras.blog_api.dto.CategoryDTO;
import com.lucasvieiras.blog_api.dto.requests.category.CategoryRequest;
import com.lucasvieiras.blog_api.entities.Category;
import com.lucasvieiras.blog_api.services.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryDTOFactory categoryDTOFactory;

    @PostMapping()
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryRequest request) {
        Category category = categoryService.createCategory(request);
        CategoryDTO response = categoryDTOFactory.create(category);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryRequest request, @PathVariable UUID id) {
        Category category = categoryService.updateCategory(request, id);
        CategoryDTO response = categoryDTOFactory.create(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<Page<CategoryDTO>> findAllCategories(Pageable pageable) {
        Page<Category> categoriesPage = categoryService.findAllCategories(pageable);

        List<CategoryDTO> categoryDTOList = categoryDTOFactory.create(categoriesPage.getContent());
        Page<CategoryDTO> categoriesDTOPage = new PageImpl<>(categoryDTOList, pageable, categoriesPage.getTotalElements());

        return new ResponseEntity<>(categoriesDTOPage, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable UUID id) {
        Category category = categoryService.findCategoryById(id);
        CategoryDTO response = categoryDTOFactory.create(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by-title/{title}")
    public ResponseEntity<CategoryDTO> findCategoryByTitle(@PathVariable String title) {
        Category category = categoryService.findCategoryByTitle(title);
        CategoryDTO response = categoryDTOFactory.create(category);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
