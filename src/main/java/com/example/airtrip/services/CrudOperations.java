package com.example.airtrip.services;

import com.example.airtrip.domain.data.CountryData;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CrudOperations <T, R>{
    void create(T data, MultipartFile file) throws IOException;
    void update(T data, MultipartFile file, Long id) throws IOException;
    List<R> findAll();
    void delete(Long id);
}
