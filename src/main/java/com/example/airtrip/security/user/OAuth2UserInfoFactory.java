package com.example.airtrip.security.user;

import com.example.airtrip.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

import static com.example.airtrip.domain.enums.AuthProvider.google;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws OAuth2AuthenticationProcessingException {
        if(registrationId.equalsIgnoreCase(google.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
