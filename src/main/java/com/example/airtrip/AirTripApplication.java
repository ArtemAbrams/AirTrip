package com.example.airtrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class AirTripApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirTripApplication.class, args);
    }

}