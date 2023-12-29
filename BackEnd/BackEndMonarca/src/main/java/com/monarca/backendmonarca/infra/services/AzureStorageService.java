package com.monarca.backendmonarca.infra.services;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class AzureStorageService {

    @Value("${azure.storage.connection-string}")
    private String connectionString;

    private final String containerName = "imagenesmonarca";
    private BlobServiceClient blobServiceClient;

    @PostConstruct
    public void initializeBlobServiceClient() {
        this.blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
    }

    public void uploadImage(String imageName, Path imagePath) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(imageName);
        blobClient.uploadFromFile(imagePath.toString());
    }

    public void downloadImage(String imageName, Path downloadPath) {
        BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
        BlobClient blobClient = containerClient.getBlobClient(imageName);
        blobClient.downloadToFile(downloadPath.toString());
    }
}