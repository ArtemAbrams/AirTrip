package com.example.airtrip.blobstorage.configuration;


import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobStorageConfiguration {
    @Value("${azure.storage.connection}")
    private String connectionString;
    @Value("${azure.storage.container.name}")
    private String containerName;
    @Bean
    public BlobServiceClient getBlobServiceClient(){
        var client = new BlobServiceClientBuilder();
        client.connectionString(connectionString);
        return client.buildClient();
    }
    @Bean
    public BlobContainerClient getBlobContainerClient(){
        return getBlobServiceClient()
                .getBlobContainerClient(containerName);
    }
}
