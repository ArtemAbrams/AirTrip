package com.example.airtrip.controller.mvccontroller;


import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import com.example.airtrip.repository.ConflictCountryRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jqueryCountry")
public class JQueryCountryController {
    private final ConflictCountryRepository countryRepository;
    @GetMapping(value = "/getNameOfCountries")
    public ResponseEntity<List<String>> getName(String term){
        return ResponseEntity.ok(countryRepository
                .findAll()
                .stream()
                .map(ConflictCountry::getName)
                .filter(e -> e.startsWith(term))
                .toList());
    }
}
