package com.example.airtrip.saleforceintegration.service.implementation;

import com.example.airtrip.saleforceintegration.configuration.AuthenticationSaleForceConfiguration;
import com.example.airtrip.saleforceintegration.dto.AuthResponse;
import com.example.airtrip.saleforceintegration.service.Authentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class AuthenticationImpl implements Authentication {
    @Autowired
    private AuthenticationSaleForceConfiguration saleForceConfiguration;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    @Qualifier(value = "requestForm")
    private MultiValueMap<String, String> requestBody;
    @Override
    public AuthResponse authentication() {
        var requestEntity = getRequestEntity();
        AuthResponse authResponse = null;
        try{
            var result = restTemplate.exchange(
                    saleForceConfiguration.getTokenUrl(),
                    HttpMethod.POST,
                    requestEntity,
                    AuthResponse.class);
            if(!Objects.isNull(result.getBody())){
                authResponse = result.getBody();
            }
        }
        catch (Exception exception){
         log.error(exception.getMessage());
        }
        return authResponse;
    }
    private HttpEntity<? extends MultiValueMap<String, String>> getRequestEntity() {
        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        return new HttpEntity<>(requestBody, httpHeaders);
    }
}
