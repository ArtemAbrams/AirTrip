package com.example.airtrip.domain.mapper.mvcmapper;

import com.example.airtrip.domain.dto.dtoformvc.ConflictCountryDTO;
import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConflictCountryMapper {
    public ConflictCountryDTO entityToDTO(ConflictCountry conflictCountry){
        return ConflictCountryDTO.builder()
                .id(conflictCountry.getId())
                .name(conflictCountry.getName())
                .flag(conflictCountry.getFlag())
                .typeOfConflict(conflictCountry.getTypeOfConflict())
                .build();
    }
}
