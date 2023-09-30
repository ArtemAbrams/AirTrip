package com.example.airtrip.domain.mapper.restapimapper;

import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.domain.dto.dtoforrestapi.CountryDTO;
import com.example.airtrip.domain.entity.entityforrestspi.Country;
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
                .id(country.getId().intValue())
                .name(country.getName())
                .file(country.getImageFile())
                .build();
    }
}
