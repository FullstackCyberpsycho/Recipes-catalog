//package com.example.Recipes.catalog.services;
//
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Service
//public class ImageStorageService {
//    private final Path root = Paths.get("uploads");
//
//    public void deleteFile(String imageUrl) {
//        try {
//            if (imageUrl != null) {
//                Path filePath = root.resolve(Paths.get(imageUrl).getFileName().toString());
//                Files.deleteIfExists(filePath);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("Ошибка при удалении файла " + imageUrl, e);
//        }
//    }
//}

