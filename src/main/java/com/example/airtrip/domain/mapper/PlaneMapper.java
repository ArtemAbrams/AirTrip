package com.example.airtrip.domain.mapper;

import com.example.airtrip.domain.data.CountryData;
import com.example.airtrip.domain.data.PlaneData;
import com.example.airtrip.domain.dto.CountryDTO;
import com.example.airtrip.domain.dto.PlaneDTO;
import com.example.airtrip.domain.entity.Country;
import com.example.airtrip.domain.entity.Plane;
import lombok.experimental.UtilityClass;

import java.io.IOException;

@UtilityClass
public class PlaneMapper {
    public Plane dataToEntity(PlaneData planeData, byte[] path){
        return Plane.builder()
                .name(planeData.getName())
                .imageFile(path)
                .colour(planeData.getColour())
                .build();
    }
    public PlaneDTO entityToDto(Plane plane) throws IOException {
        return PlaneDTO.builder()
                .colour(plane.getColour())
                .pathToImage(plane.getImageFile())
                .name(plane.getName())
                .build();
    }
}
