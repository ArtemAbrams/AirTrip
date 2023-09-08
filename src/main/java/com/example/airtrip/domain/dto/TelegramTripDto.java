package com.example.airtrip.domain.dto;

import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TelegramTripDto {
    private double price;
    private PlaneDTO planeDTO;
    private LocalDateTime departureDate;
    private CountryDTO countryDTO;
}
