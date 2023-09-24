package com.example.airtrip.domain.dto.dtoforrestapi;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AirTripDTO implements Serializable {
    private double price;
    private boolean enabled;
    private CountryDTO fromCountry;
    private CountryDTO toCountry;
    private LocalDateTime departureDate;
    private PlaneDTO plane;
    private byte[] ImageOfAirCompany;
}
