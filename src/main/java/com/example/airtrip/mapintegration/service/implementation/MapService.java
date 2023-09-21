package com.example.airtrip.mapintegration.service.implementation;


import com.example.airtrip.mapintegration.service.Map;
import com.example.airtrip.mapintegration.ResponseFromMapServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class MapService implements Map {
    @Value("${map.api.key}")
    private String key;
    private String generateUri(String countryName){
        return  UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("maps.googleapis.com")
                .path("/maps/api/geocode/json")
                .queryParam("address", countryName)
                .queryParam("key", key)
                .toUriString();
    }
    @Override
    public ResponseFromMapServer getCoordinate(String countryName){
        var uri = generateUri(countryName);
        var response =
                new RestTemplate().getForEntity(uri, ResponseFromMapServer.class);
        if(!response.getStatusCode()
                .is2xxSuccessful())
            return null;
        return response.getBody();
    }

}
