package com.example.Recipes.catalog.services;

import com.example.Recipes.catalog.models.Difficulty;
import com.example.Recipes.catalog.models.Recipe;
import com.example.Recipes.catalog.repository.FavoriteRepository;
import com.example.Recipes.catalog.repository.RecipeRepository;
import jakarta.transaction.Transactional;
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
    private final FavoriteRepository favoriteRepository;
    private static final Logger log = LoggerFactory.getLogger(RecipeService.class);
    private final FavoriteService favoriteService;
    private final ImageService imageService;
    private final Recipe recipe;

    @Autowired
    public RecipeService(RecipeRepository repository, FavoriteRepository favoriteRepository,
                         FavoriteService favoriteService, ImageService imageService, Recipe recipe) {
        this.repository = repository;
        this.favoriteRepository = favoriteRepository;
        this.favoriteService = favoriteService;
        this.imageService = imageService;
        this.recipe = recipe;
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

        // если у рецепта есть старое изображение и приходит новое → удалить старое
        if (updatedRecipe.getImageUrl() != null && recipe.getImageUrl() != null &&
                !updatedRecipe.getImageUrl().equals(recipe.getImageUrl())) {
            imageService.deleteImage(recipe.getImageUrl());
        }

        // если фронт прислал imageUrl = null (удаление картинки)
        if (updatedRecipe.getImageUrl() == null && recipe.getImageUrl() != null) {
            imageService.deleteImage(recipe.getImageUrl());
        }

            recipe.setName(updatedRecipe.getName());
            recipe.setDescription(updatedRecipe.getDescription());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setInstructions(updatedRecipe.getInstructions());
            recipe.setCookingTime(updatedRecipe.getCookingTime());
            recipe.setImageUrl(updatedRecipe.getImageUrl());
            recipe.setDifficulty(updatedRecipe.getDifficulty());
            recipe.setCategory(updatedRecipe.getCategory());

            favoriteService.evictFavoriteCache();
            log.info("Обнавлен рецепт: " + recipe.getName());
            return repository.save(recipe);
    }

    @Transactional
    @CacheEvict(value = "recipes", allEntries = true)
    public void deleteByIdRecipe(long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Рецепт не найден с id: " + id);
        }

        // удаляем картинку с диска
        if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) {
            imageService.deleteImage(recipe.getImageUrl());
        }

        log.info("Удален рецепт с id: " + id);

        favoriteRepository.deleteByRecipeId(id);
        repository.deleteById(id);
        favoriteService.evictFavoriteCache();
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
        log.info("Поиск названий рецептов отсортированных по DESC");
        return repository.findAll(Sort.by(Sort.Direction.DESC, "name"));
    }

    @Cacheable(value = "recipes", key = "'sortedCookingTimeAsc'")
    public List<Recipe> findAllSortedCookingTimeAsc() {
        log.info("Поиск рецептов по времени готовки отсортированных по ASC");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "cookingTime"));
    }

    @Cacheable(value = "recipes", key = "'sortedCookingTimeDesc'")
    public List<Recipe> findAllSortedCookingTimeDesc() {
        log.info("Поиск рецептов по времени готовки отсортированных по DESC");
        return repository.findAll(Sort.by(Sort.Direction.DESC, "cookingTime"));
    }

    @Cacheable(value = "recipes", key = "'name_' + #name")
    public List<Recipe> findAllByNameContainingIgnoreCase(String name) {
        log.info("Поиск рецептов по названию: " + name);
        return repository.findAllByNameContainingIgnoreCase(name);
    }

    @Cacheable(value = "recipes", key = "'difficulty_' + #difficulty")
    public List<Recipe> findByDifficulty(Difficulty difficulty) {
        log.info("Поиск рецептов по сложности: " + difficulty);
        return repository.findByDifficulty(difficulty);
    }

    @Cacheable(value = "recipes", key = "'category_' + #id")
    public List<Recipe> findByCategoryId(Long id) {
        log.info("Поиск рецептов по категории с id: " + id);
        return repository.findByCategoryId(id);
    }

    @CacheEvict(value = "recipes", allEntries = true)
    public void evictRecipesCache() {
        log.info("Очистка кеша рецептов");
    }
}
