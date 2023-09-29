package com.example.airtrip.kafka.configuration;

import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.domain.data.dataforrestapi.PlaneData;
import com.example.airtrip.domain.dto.dtoforrestapi.CountryDTO;
import com.example.airtrip.domain.entity.entityforrestspi.Country;
import com.example.airtrip.domain.mapper.restapimapper.CountryMapper;
import com.example.airtrip.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final CountryRepository countryRepository;
    @KafkaListener(
            topics = "country",
            groupId = "createCountry"
    )
    public CountryDTO listener(CountryData countryData) throws IOException {
        var entity = Country.builder()
                .name(countryData.getName())
                .build();
        countryRepository.saveAndFlush(entity);
        return CountryMapper.entityToDto(entity);
    }
}
