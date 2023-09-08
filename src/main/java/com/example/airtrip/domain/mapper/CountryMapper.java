package com.example.airtrip.domain.mapper;

import com.example.airtrip.domain.data.CountryData;
import com.example.airtrip.domain.dto.CountryDTO;
import com.example.airtrip.domain.entity.Country;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class CountryMapper {
    public Country dataToEntity(CountryData countryData,byte[] path){
        return Country.builder()
                .name(countryData.getName())
                .imageFile(path)
                .build();
    }
    public CountryDTO entityToDto(Country country) throws IOException {
        return CountryDTO.builder()
                .id(country.getId())
                .name(country.getName())
                .file(country.getImageFile())
                .build();
    }
}
