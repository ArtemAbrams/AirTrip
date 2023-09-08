package com.example.airtrip.domain.dto;

import com.example.airtrip.domain.data.PlaneData;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirTripDTO {
    private double price;
    private boolean enabled;
    private CountryDTO fromCountry;
    private CountryDTO toCountry;
    private LocalDateTime departureDate;
    private PlaneDTO plane;
    private byte[] ImageOfAirCompany;
}
