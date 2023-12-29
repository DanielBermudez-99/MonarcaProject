package com.monarca.backendmonarca.controller;

import com.monarca.backendmonarca.infra.services.AzureStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

@RestController
@RequestMapping("/product/images")
public class AzureStorageController {

    private final AzureStorageService azureStorageService;

    public AzureStorageController(AzureStorageService azureStorageService) {
        this.azureStorageService = azureStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        try {
            String imageName = image.getOriginalFilename();
            azureStorageService.uploadImage(imageName, Paths.get(image.getOriginalFilename()));
            return new ResponseEntity<>("Image uploaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{imageName}")
    public ResponseEntity<String> downloadImage(@PathVariable String imageName) {
        try {
            azureStorageService.downloadImage(imageName, Paths.get(imageName));
            return new ResponseEntity<>("Image downloaded successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}