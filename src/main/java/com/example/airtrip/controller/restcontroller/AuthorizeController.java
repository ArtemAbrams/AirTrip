package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.LoginData;
import com.example.airtrip.domain.data.dataforrestapi.RegistrationData;
import com.example.airtrip.domain.dto.dtoforrestapi.TokenResponse;
import com.example.airtrip.services.implementation.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/authorize")
public class AuthorizeController {
    private final AuthenticationServiceImpl authenticationServiceImpl;
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@RequestBody RegistrationData registrationData){
       return ResponseEntity.ok(authenticationServiceImpl.registration(registrationData));
    }
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginData loginData){
        return ResponseEntity.ok(authenticationServiceImpl.login(loginData));
    }
}
