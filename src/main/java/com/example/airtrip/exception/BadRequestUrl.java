package com.example.airtrip.exception;

public class BadRequestUrl extends RuntimeException{
    public BadRequestUrl(String message){
        super(message);
    }
}
