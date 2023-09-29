package com.example.airtrip.blobstorage.service;

import org.springframework.web.multipart.MultipartFile;

public interface AzurePhotoBlobStorage {
    boolean delete(String path);
    String upload(MultipartFile multipartFile);
    byte[] download(String path);
}
