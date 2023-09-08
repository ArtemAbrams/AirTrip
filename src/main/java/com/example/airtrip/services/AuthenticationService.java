package com.example.airtrip.services;

import com.example.airtrip.domain.data.LoginData;
import com.example.airtrip.domain.data.RegistrationData;
import com.example.airtrip.domain.dto.TokenResponse;
import org.springframework.stereotype.Service;


public interface AuthenticationService {
    String registration(RegistrationData registrationData);
    TokenResponse login(LoginData loginData);
}
