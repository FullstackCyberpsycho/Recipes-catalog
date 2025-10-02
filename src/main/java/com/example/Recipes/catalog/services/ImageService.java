package com.example.Recipes.catalog.services;

import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {
    private final Path root = Paths.get("uploads"); // папка uploads в корне проекта

    public boolean deleteImage(String imageUrl) {
        try {
            if (imageUrl == null || imageUrl.isEmpty()) return false;

            // достаём только имя файла
            String filename = Paths.get(imageUrl).getFileName().toString();
            Path file = root.resolve(filename);

            return Files.deleteIfExists(file);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

