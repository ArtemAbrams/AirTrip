package com.example.airtrip.services;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface CrudOperations <T, R>{
    R create(T data, MultipartFile file) throws IOException;
    R update(T data, MultipartFile file, Long id) throws IOException;
    List<R> findAll();
    void delete(Long id);
    Page<R> findAll(Long page, Long size);
    R getById(Long Id) throws IOException, ExecutionException, InterruptedException;
}
