package com.example.Recipes.catalog.services;

import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.repository.CategoryRepository;
import com.example.Recipes.catalog.repository.RecipeRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;

@Service
public class RecipeService {
    private final RecipeRepository repository;
    private final CategoryRepository categoryRepository;
    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    private final FavoriteService favoriteService;

    @Autowired
    public RecipeService(RecipeRepository repository, CategoryRepository categoryRepository, FavoriteService favoriteService) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.favoriteService = favoriteService;
    }

    @CacheEvict(value = "recipes", allEntries = true)
    public Recipe saveRecipe(Recipe recipe) {
        log.info("Добавлен рецепт: " + recipe.getName());
        return repository.save(recipe);
    }

    @CacheEvict(value = "recipes", allEntries = true)
    public Recipe updateRecipe(long id, Recipe updatedRecipe) {
        Recipe recipe = repository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Рецепт не найден с id: " + id));
            recipe.setName(updatedRecipe.getName());
            recipe.setDescription(updatedRecipe.getDescription());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setInstructions(updatedRecipe.getInstructions());
            recipe.setCookingTime(updatedRecipe.getCookingTime());
            recipe.setImageUrl(updatedRecipe.getImageUrl());
            recipe.setDifficulty(updatedRecipe.getDifficulty());
            recipe.setCategory(updatedRecipe.getCategory());

            // Сбрасываем кеш избранного, чтобы обновлённый рецепт отобразился сразу
            favoriteService.evictFavoriteCache();

            log.info("Обнавлен рецепт: " + recipe.getName());
            return repository.save(recipe);
    }

    @CacheEvict(value = "recipes", allEntries = true)
    public void deleteByIdRecipe(long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Рецепт не найден с id: " + id);
        }
        log.info("Удален рецепт с id: " + id);
        repository.deleteById(id);
    }

    @Cacheable(value = "recipes", key = "'allRecipes'")
    public List<Recipe> findAllRecipe() {
        log.info("Поиск всех рецептов");
        return repository.findAll();
    }

    @Cacheable(value = "recipes", key = "'recipe_' + #id")
    public Optional<Recipe> findByIdRecipe(long id) {
        log.info("Поиск рецепта по id: " + id);
        return repository.findById(id);
    }

    @Cacheable(value = "recipes", key = "'sortedNameAsc'")
    public List<Recipe> findAllSortedNameAsc() {
        log.info("Поиск названий рецептов отсортированых по ASC");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Cacheable(value = "recipes", key = "'sortedNameDesc'")
    public List<Recipe> findAllSortedNameDesc() {
        log.info("Поиск названий рецептов отсортированых по DESC");
        return repository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }

    @Cacheable(value = "recipes", key = "'sortedCookingTimeAsc'")
    public List<Recipe> findAllSortedCookingTimeAsc() {
        log.info("Поиск рецептов по временни готовки отсортированых по ASC");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "cookingTime"));
    }

    @Cacheable(value = "recipes", key = "'sortedCookingTimeDesc'")
    public List<Recipe> findAllSortedCookingTimeDesc() {
        log.info("Поиск рецептов по временни готовки отсортированых по DESC");
        return repository.findAll(Sort.by(Sort.Direction.DESC, "cookingTime"));
    }

    @Cacheable(value = "recipes", key = "'name_' + #name")
    public List<Recipe> findAllByNameContainingIgnoreCase(String name) {
        log.info("Поиск рецептов по названию: " + name);
        return repository.findAllByNameContainingIgnoreCase(name);
    }
}
