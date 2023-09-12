package com.example.airtrip.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CrudOperations <T, R>{
    void create(T data, MultipartFile file) throws IOException;
    void update(T data, MultipartFile file, Long id) throws IOException;
    List<R> findAll();
    void delete(Long id);
}
