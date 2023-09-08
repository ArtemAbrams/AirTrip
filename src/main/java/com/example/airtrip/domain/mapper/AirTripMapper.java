package com.example.airtrip.domain.mapper;

import com.example.airtrip.domain.data.AirTripData;
import com.example.airtrip.domain.dto.AirTripDTO;
import com.example.airtrip.domain.dto.TelegramTripDto;
import com.example.airtrip.domain.entity.AirTrip;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class AirTripMapper {
    public AirTrip dataToEntity(AirTripData data, byte[] path){
        return AirTrip.builder()
                .price(data.getPrice())
                .enabled(data.isEnabled())
                .departureDate(data.getDepartureDate())
                .ImageOfAirCompany(path)
                .build();
    }
    public AirTripDTO entityToDto(AirTrip airTrip) throws IOException {
        return AirTripDTO.builder()
                .price(airTrip.getPrice())
                .ImageOfAirCompany(airTrip.getImageOfAirCompany())
                .enabled(airTrip.isEnabled())
                .departureDate(airTrip.getDepartureDate())
                .plane(PlaneMapper.entityToDto(airTrip.getPlane()))
                .toCountry(CountryMapper.entityToDto(airTrip.getToCountry()))
                .fromCountry(CountryMapper.entityToDto(airTrip.getFromCountry()))
                .build();
    }
    public TelegramTripDto entityToTelegramDto(AirTrip airTrip) throws IOException {
        return TelegramTripDto.builder()
                .price(airTrip.getPrice())
                .departureDate(airTrip.getDepartureDate())
                .planeDTO(PlaneMapper.entityToDto(airTrip.getPlane()))
                .countryDTO(CountryMapper.entityToDto(airTrip.getToCountry()))
                .build();
    }
}