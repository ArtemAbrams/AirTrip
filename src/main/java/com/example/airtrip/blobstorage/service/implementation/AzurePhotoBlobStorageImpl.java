package com.example.airtrip.blobstorage.service.implementation;

import com.azure.storage.blob.BlobContainerClient;
import com.example.airtrip.blobstorage.service.AzurePhotoBlobStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class AzurePhotoBlobStorageImpl implements AzurePhotoBlobStorage {
    private final BlobContainerClient getBlobContainerClient;
    @Override
    public boolean delete(String path) {
        return false;
    }

    @Override
    public String upload(MultipartFile multipartFile) {
        return null;
    }

    @Override
    public byte[] download(String path) {
        return new byte[0];
    }
}
