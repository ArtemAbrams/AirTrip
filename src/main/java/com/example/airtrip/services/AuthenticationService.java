package com.example.airtrip.services;

import com.example.airtrip.domain.data.dataforrestapi.LoginData;
import com.example.airtrip.domain.data.dataforrestapi.RegistrationData;
import com.example.airtrip.domain.dto.dtoforrestapi.TokenResponse;


public interface AuthenticationService {
    String registration(RegistrationData registrationData);
    TokenResponse login(LoginData loginData);
}
