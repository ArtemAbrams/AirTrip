package com.example.airtrip.saleforceintegration.service.implementation;

import com.azure.core.util.UrlBuilder;
import com.example.airtrip.saleforceintegration.service.ExtractData;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExtractDataImpl implements ExtractData {
    private final AuthenticationImpl authentication;
    private final RestTemplate restTemplate;
    private final static String PATH = "/services/data/v52.0/query?q=";
    @Override
    public Map getResult(String query,  HttpMethod httpMethod, Class<? extends Map> response) {
        var loginResponse = authentication.authentication();
        var url = loginResponse.getInstance_url() + PATH + query;
        var headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ loginResponse.getAccess_token());
        var requestEntity = new HttpEntity<String>("", headers);
        var result = restTemplate.exchange(
                url,
                httpMethod,
                requestEntity,
                response
        );
        return result.getBody();
    }
}
