package com.example.airtrip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({AirTripNotFoundException.class, PlaneNotFoundException.class, CountryNotFoundException.class})
    public final ResponseEntity<Object> handlerNotFound(Exception ex, WebRequest webRequest){
        var notFound = new NotFound(ex.getMessage(), LocalDateTime.now(), webRequest.getDescription(true));
        return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
    }
}
