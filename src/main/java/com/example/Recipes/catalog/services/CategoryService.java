package com.example.Recipes.catalog.services;

import com.example.Recipes.catalog.models.Category;
import com.example.Recipes.catalog.repository.CategoryRepository;
import com.example.Recipes.catalog.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final RecipeService recipeService;
    private final FavoriteService favoriteService;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,RecipeService recipeService,
                           FavoriteService favoriteService) {
        this.categoryRepository = categoryRepository;
        this.recipeService = recipeService;
        this.favoriteService = favoriteService;
    }

    @Cacheable(value = "category", key = "'allCategory'")
    public List<Category> getAllCategories() {
        log.info("Поиск всех категрий");
        return categoryRepository.findAll();
    }

    @CacheEvict(value = "category", allEntries = true)
    public Category addCategory(Category category) {
        log.info("Добавлена категория: " + category.getName());
       return categoryRepository.save(category);
    }

    @Cacheable(value = "category", key = "'category_' + #id")
    public Optional<Category> getCategory(Long id) {
        log.info("Поиск категории с id: " + id);
        return categoryRepository.findById(id);
    }

    @CacheEvict(value = "category", allEntries = true)
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);

        recipeService.evictRecipesCache();
        favoriteService.evictFavoriteCache();
        log.info("Категория с id: {} бала удалена", id);
    }

    @CacheEvict(value = "category", allEntries = true)
    @Transactional
    public Category updateCategory(long id ,Category updateCategory) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Категория не найден с id: " + id));
        category.setName(updateCategory.getName());
        log.info("Название категрии с id {} была изменена на {}", id, updateCategory.getName());

        recipeService.evictRecipesCache();
        favoriteService.evictFavoriteCache();

        return categoryRepository.save(category);
    }
}
