package com.example.airtrip.domain.dto.dtoforrestapi;

import com.example.airtrip.domain.dto.dtoforrestapi.CountryDTO;
import com.example.airtrip.domain.dto.dtoforrestapi.PlaneDTO;
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
