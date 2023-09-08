package com.example.airtrip.controller;

import com.example.airtrip.domain.data.CountryData;
import com.example.airtrip.domain.dto.CountryDTO;
import com.example.airtrip.domain.mapper.CountryMapper;
import com.example.airtrip.exception.CountryNotFoundException;
import com.example.airtrip.repository.CountryRepository;
import com.example.airtrip.services.implementation.CountryCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/country")
@Slf4j
public class CountryController {
    private final CountryCrud countryCrud;
    private final CountryRepository countryRepository;
    @PostMapping("/create")
    public ResponseEntity<String> createCountry(@RequestPart("data") CountryData countryData,
                                               @RequestPart("file") MultipartFile file){
        try {
            countryCrud.create(countryData, file);
            return ResponseEntity.ok()
                    .body("OK");

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body("ERROR");
        }
    }
    @PutMapping(value = "/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> updateCountry(@RequestPart("data") CountryData countryData,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart("id") Long id){
        try {
            countryCrud.update(countryData, file, id);
            return ResponseEntity.ok()
                    .body("OK");

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body("ERROR");
        }

    }
    @GetMapping("/getAll")
    public ResponseEntity<List<CountryDTO>> findAll(){
        return ResponseEntity.ok()
                .body(countryCrud.findAll());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id){
        try {
            countryCrud.delete(id);
            return ResponseEntity.ok()
                    .body("OK");

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }
    @GetMapping ("/getCountry")
    public ResponseEntity<?> getById(@RequestParam Long id){
        try {
            var country = countryRepository.findById(id)
                            .orElseThrow(() -> new CountryNotFoundException("Country with " + id + " was not found "));
            return ResponseEntity.ok()
                    .body(CountryMapper.entityToDto(country));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }
}
