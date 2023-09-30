package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.services.implementation.CountryCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;


@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
@Slf4j
public class CountryController {
    private final CountryCrud countryCrud;
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> createCountry(@RequestPart("data") CountryData countryData,
                                     @RequestPart("file") MultipartFile file){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryCrud
                            .create(countryData,
                                    file));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> updateCountry(@RequestPart("data") CountryData countryData,
                                           @RequestPart("file") MultipartFile file,
                                                @RequestPart("id") Long id){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryCrud.update(countryData, file, id));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }

    }
    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(){
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryCrud.findAll());
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }

    }
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> delete(@RequestParam Long id){
        try {
            countryCrud.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping ("/getCountryById")
    public ResponseEntity<?> getById(@RequestParam Long id){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryCrud
                            .getById(id));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/pageFind")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<?> getAll(@RequestParam Long page,
                                 @RequestParam Long size){
        try {
             return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(countryCrud.findAll(page, size));
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
}
