package com.example.Recipes.catalog.controllers;

import com.example.Recipes.catalog.models.Category;
import com.example.Recipes.catalog.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody @Valid Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCategory(category));
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategory(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getCategory(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> getDeleteCategory(@PathVariable Long id) {
        service.deleteCategory(id);
        return ResponseEntity.ok("Категория была удалена");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> getUpdateCategory(@PathVariable Long id, @RequestBody @Valid Category category) {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateCategory(id, category));
    }
}
