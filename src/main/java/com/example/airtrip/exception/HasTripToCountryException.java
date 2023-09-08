package com.example.airtrip.exception;

public class HasTripToCountryException extends RuntimeException{
    public HasTripToCountryException(String message){
        super(message);
    }
}
