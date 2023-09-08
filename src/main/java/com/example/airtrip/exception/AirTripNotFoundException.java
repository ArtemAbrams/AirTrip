package com.example.airtrip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AirTripNotFoundException extends RuntimeException{
    public AirTripNotFoundException(String message){
        super(message);
    }
}
