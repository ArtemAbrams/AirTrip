package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.domain.dto.dtoforrestapi.CountryDTO;
import com.example.airtrip.domain.dto.dtoforrestapi.ResponseDTO;
import com.example.airtrip.domain.mapper.restapimapper.CountryMapper;
import com.example.airtrip.exception.CountryNotFoundException;
import com.example.airtrip.repository.CountryRepository;
import com.example.airtrip.services.implementation.CountryCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
@Slf4j
public class CountryController {
    private final CountryCrud countryCrud;
    private final CountryRepository countryRepository;
    @PostMapping("/create")
    public ResponseDTO<?> createCountry(@RequestPart("data") CountryData countryData,
                                     @RequestPart("file") MultipartFile file){
        try {
            return ResponseDTO.data(countryCrud.create(countryData, file),
                    HttpStatus.OK);

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseDTO<?> updateCountry(@RequestPart("data") CountryData countryData,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart("id") Long id){
        try {
            return ResponseDTO.data(countryCrud.update(countryData, file, id),
                    HttpStatus.OK);

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getAll")
    public ResponseDTO<?> findAll(){
        try{
            return ResponseDTO.data(countryCrud.findAll(),
                    HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @DeleteMapping("/delete")
    public ResponseDTO<?> delete(@RequestParam Long id){
        try {
            countryCrud.delete(id);
            return ResponseDTO.success();
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping ("/getCountryById")
    public ResponseDTO<?> getById(@RequestParam Long id){
        try {
            return ResponseDTO.data(countryCrud.getById(id),
                    HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/pageFind")
    public ResponseDTO<?> getAll(@RequestParam Long page,
                                 @RequestParam Long size){
        try {
            return ResponseDTO.data(countryCrud.findAll(page, size),
                    HttpStatus.OK);
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
