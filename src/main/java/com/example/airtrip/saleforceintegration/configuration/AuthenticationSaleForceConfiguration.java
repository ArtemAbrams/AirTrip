package com.example.airtrip.saleforceintegration.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Configuration
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthenticationSaleForceConfiguration {
    @Value("${salesforce.consumer-key}")
    private String key;
    @Value("${salesforce.consumer-secret}")
    private String secret;
    @Value("${salesforce.username}")
    private String username;
    @Value("${salesforce.password}")
    private String password;
    @Value("${salesforce.token-url}")
    private String tokenUrl;
    @Bean(name = "requestForm")
    public MultiValueMap<String, String> getAuthForm(){
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "password");
        requestBody.add("client_id", key);
        requestBody.add("client_secret", secret);
        requestBody.add("username", username);
        requestBody.add("password", password);
        return requestBody;
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
