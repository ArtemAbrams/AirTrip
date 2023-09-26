package com.example.airtrip.saleforceintegration.controller;


import com.example.airtrip.saleforceintegration.service.implementation.AuthenticationImpl;
import com.example.airtrip.saleforceintegration.service.implementation.ExtractDataImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/saleforce")
public class AccountController {
    private final ExtractDataImpl extractData;
    private static final String ALL_PROFILE = "SELECT Description, Name, UserType FROM Profile";
    private static final String ALL_CONTACT = "SELECT Name, Email FROM Contact ORDER BY Name ASC";
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllProfiles(){
        try {
            var data = extractData.getResult(ALL_PROFILE, HttpMethod.GET, HashMap.class);
            return ResponseEntity.ok()
                    .body(data);
        }
        catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/getContact")
    public ResponseEntity<?> getCountByUserType(){
        try {
            var data = extractData.getResult(ALL_CONTACT, HttpMethod.GET, HashMap.class);
            return ResponseEntity.ok()
                    .body(data);
        }
        catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(exception.getMessage());
        }
    }
}
