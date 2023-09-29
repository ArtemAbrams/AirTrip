package com.example.airtrip.blobstorage.service.implementation;

import com.azure.storage.blob.BlobContainerClient;
import com.example.airtrip.blobstorage.service.AzurePhotoBlobStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AzurePhotoBlobStorageImpl implements AzurePhotoBlobStorage {
    private final BlobContainerClient getBlobContainerClient;
    @Override
    public boolean delete(String path) {
        try {
            if(!isPathNotNullAndNotEmpty(path)){
                return false;
            }
            var client = getBlobContainerClient.getBlobClient(path);
            client.delete();
        }
        catch (Exception exception){
           log.error(exception.getMessage());
           return false;
        }
        return true;
    }

    @Override
    public String upload(MultipartFile multipartFile) {
        try {
            if(!multipartFile.isEmpty() && multipartFile.getBytes().length!=0){
                var blobName = generateBlobName();
                var blobClient = getBlobContainerClient.getBlobClient(blobName);
                var inputStream = multipartFile.getInputStream();
                blobClient.upload(inputStream, false);
                return blobName;
            }
        }
        catch (Exception exception){
           log.error(exception.getMessage());
        }
        return null;
    }

    @Override
    public byte[] download(String path) {

        try{
            if(isPathNotNullAndNotEmpty(path)){
                var blob = getBlobContainerClient.getBlobClient(path);
                var outputStream = new ByteArrayOutputStream();
                blob.downloadStream(outputStream);
                return outputStream.toByteArray();
            }
        }
        catch (Exception exception){
            log.error(exception.getMessage());
        }
        return null;
    }

    @Override
    public String update(MultipartFile multipartFile, String path) {
        try {
            if(!isPathNotNullAndNotEmpty(path)){
                path = generateBlobName();
            }
            if(!multipartFile.isEmpty() && multipartFile.getBytes().length!=0){
                var blobClient = getBlobContainerClient.getBlobClient(path);
                var inputStream = multipartFile.getInputStream();
                blobClient.upload(inputStream, true);
                return path;
            }
        }
        catch (Exception exception){
            log.error(exception.getMessage());
        }
        return path;
    }

    private boolean isPathNotNullAndNotEmpty(String path){
        if(path!=null && !path.isEmpty())
            return true;
        else
            return false;
    }
    private String generateBlobName(){
        return UUID.randomUUID().toString();
    }
}
