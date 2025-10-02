package com.example.Recipes.catalog.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final Path root = Paths.get("uploads"); // в корне проекта будет папка uploads

    public ImageController() throws IOException {
        if (!Files.exists(root)) {
            Files.createDirectories(root);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = root.resolve(filename);
            Files.copy(file.getInputStream(), filePath);

            // URL, который сохраняется в БД (для фронта)
            String fileUrl = "/uploads/" + filename;
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка загрузки файла");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@RequestParam String path) {
        try {
            // достаём только имя файла из полного пути
            String fileName = Paths.get(path).getFileName().toString();
            Path filePath = root.resolve(fileName);

            Files.deleteIfExists(filePath); // удаляем из uploads

            return ResponseEntity.ok("Изображение удалено");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка удаления файла");
        }
    }
}

