package com.example.airtrip.kafka.Listener;


import com.example.airtrip.exception.*;
import com.example.airtrip.repository.AirTripRepository;
import com.example.airtrip.repository.CountryRepository;
import com.example.airtrip.repository.PlaneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class KafkaListeners {
    private final CountryRepository countryRepository;
    private final PlaneRepository planeRepository;
    private final AirTripRepository airTripRepository;
    @KafkaListener(
            topics = "deletePlane",
            groupId = "groupId"
    )
    public void deletePlane(Long id)  {
        var plane = planeRepository.findById(id)
                .orElseThrow(()-> new PlaneNotFoundException("Plane with " + id + " was not found"));
        if(plane.getAirTripList().isEmpty())
            planeRepository.delete(plane);
        else
            throw new PlaneHasTripException("You cannot do this action because this plane has trips");
    }
    @KafkaListener(
            topics = "deleteCountry",
            groupId = "groupId"
    )
    @Transactional
    public void deleteCountry(Long id)  {
        var entity = countryRepository.findById(id)
                .orElseThrow(() -> new CountryNotFoundException("Country with " + id + " was not found"));
        if (entity.getFinalRoutes().isEmpty() && entity.getInitialRoutes().isEmpty())
            countryRepository.delete(entity);
        else
            throw new HasTripToCountryException("Have trips to this country");
    }
    @KafkaListener(
            topics = "deleteAirTrip",
            groupId = "groupId"
    )
    public void deleteAirTrip(Long id) {
        var airTrip = airTripRepository.findById(id)
                .orElseThrow(() -> new AirTripNotFoundException("Air trip with "+ id + " was not found"));
        airTripRepository.delete(airTrip);
    }
}
