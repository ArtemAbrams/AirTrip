package com.example.airtrip.security.user;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        return(String) attributes.get("given_name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getLastName() {
        return (String) attributes.get("family_name");
    }

    @Override
    public boolean isEmailEnable() {
        return (boolean) attributes.get("email_verified");
    }
}
