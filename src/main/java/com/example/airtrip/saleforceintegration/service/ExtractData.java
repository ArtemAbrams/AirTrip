package com.example.airtrip.saleforceintegration.service;


import org.springframework.http.HttpMethod;

import java.util.Map;

public interface ExtractData {
    Map getResult(String query, HttpMethod httpMethod, Class<? extends Map> response);
}
