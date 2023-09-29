package com.example.airtrip.domain.mapper.mvcmapper;

import com.example.airtrip.blobstorage.service.implementation.AzurePhotoBlobStorageImpl;
import com.example.airtrip.domain.dto.dtoformvc.ConflictCountryDTO;
import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ConflictCountryMapper {
    private final AzurePhotoBlobStorageImpl azurePhotoBlobStorage;
    public ConflictCountryDTO entityToDTO(ConflictCountry conflictCountry){
        return ConflictCountryDTO.builder()
                .id(conflictCountry.getId())
                .name(conflictCountry.getName())
                .flag(azurePhotoBlobStorage.download(conflictCountry.getFlag()))
                .typeOfConflict(conflictCountry.getTypeOfConflict())
                .build();
    }
}
