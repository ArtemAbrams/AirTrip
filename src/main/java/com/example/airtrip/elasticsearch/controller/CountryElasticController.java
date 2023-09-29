package com.example.airtrip.elasticsearch.controller;

import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.elasticsearch.elasticentity.CountryElastic;
import com.example.airtrip.elasticsearch.elasticrepository.CountryElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/elastic-country")
@Slf4j
@RequiredArgsConstructor
public class CountryElasticController {

    private final CountryElasticRepository countryElasticRepository;
    @PostMapping("/create")
    public ResponseEntity<?> createCountry(@RequestPart("data") CountryData countryData,
                                           @RequestPart("file") MultipartFile file) {
        try {
            var id = UUID.randomUUID().toString();
            var entity = CountryElastic.builder()
                    .name(countryData.getName())
                    .id(id)
                    .build();
            if(file!=null && file.getBytes().length!=0){
                entity.setImageFile(file.getBytes());
            }
            entity = countryElasticRepository.save(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(entity);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/findAll")
    public ResponseEntity<?> getAll() {
        try {
            var countries = countryElasticRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(countries);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/findAllThatStarts")
    public ResponseEntity<?> findAllThatStarts(@RequestParam("name") String startWith) {
        try {
            var countries = countryElasticRepository.findByNameLike(startWith + "%");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(countries);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/paging")
    public ResponseEntity<?> paging(@RequestParam("page") Long page,
                                    @RequestParam("size") Long size){
        try {
            var countries = countryElasticRepository.findAll(PageRequest.of(
                    Math.toIntExact(page),
                    Math.toIntExact(size)));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(countries);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") String id){
        try {
            countryElasticRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Delete was successfully");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage() +
                            " Delete was unsuccessfully");
        }
    }
}
