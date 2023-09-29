package com.example.airtrip;

import com.example.airtrip.domain.data.dataforrestapi.PlaneData;
import com.example.airtrip.domain.enums.Colour;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@EnableCaching
@EnableKafka
public class AirTripApplication {
    public static void main(String[] args) {
        SpringApplication.run(AirTripApplication.class, args);
    }
}
