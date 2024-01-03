package com.monarca.backendmonarca.infra.services;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AzureStorageService {

    private final BlobContainerClient containerClient;

    public AzureStorageService(@Value("${azure.storage.connection-string}") String connectionString,
                               @Value("${azure.storage.container-name}") String containerName) {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        this.containerClient = blobServiceClient.getBlobContainerClient(containerName);
    }

    public String upload(MultipartFile file) throws IOException {
        String blobName = file.getOriginalFilename();
        BlobClient blobClient = containerClient.getBlobClient(blobName);
        try (InputStream inputStream = file.getInputStream()) {
            blobClient.upload(inputStream, file.getSize());
        }
        return blobClient.getBlobUrl();
    }
}