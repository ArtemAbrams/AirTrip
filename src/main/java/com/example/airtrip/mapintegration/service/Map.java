package com.example.airtrip.mapintegration.service;

import com.example.airtrip.mapintegration.ResponseFromMapServer;

public interface Map {
    public ResponseFromMapServer getCoordinate(String countryName);
}
