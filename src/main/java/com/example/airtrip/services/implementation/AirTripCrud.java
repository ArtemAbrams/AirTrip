package com.example.airtrip.services.implementation;

import com.example.airtrip.domain.data.dataforrestapi.AirTripData;
import com.example.airtrip.domain.dto.dtoforrestapi.AirTripDTO;
import com.example.airtrip.domain.mapper.restapimapper.AirTripMapper;
import com.example.airtrip.exception.AirTripNotFoundException;
import com.example.airtrip.exception.CountryNotFoundException;
import com.example.airtrip.exception.PlaneNotFoundException;
import com.example.airtrip.repository.AirTripRepository;
import com.example.airtrip.repository.CountryRepository;
import com.example.airtrip.repository.PlaneRepository;
import com.example.airtrip.services.CrudOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirTripCrud implements CrudOperations<AirTripData,AirTripDTO> {
    private final CountryRepository countryRepository;
    private final PlaneRepository planeRepository;
    private final AirTripRepository airTripRepository;
    private final TelegramBot telegramBot;
    @Override
    public void create(AirTripData data, MultipartFile file) throws IOException {
        var plane = planeRepository.findById(data.getPlaneId())
                .orElseThrow(() -> new PlaneNotFoundException("Plane with " + data.getPlaneId() + " was not found"));
        var airTrip = AirTripMapper.dataToEntity(data, file.getBytes());
        var fromCountry = countryRepository.findById(data.getFromCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country with " + data.getFromCountryId() + " was not found" ));
        var toCountry = countryRepository.findById(data.getToCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country with " + data.getToCountryId() + " was not found" ));
        airTrip.setPlane(plane);
        airTrip.setToCountry(toCountry);
        airTrip.setFromCountry(fromCountry);
        var entity = airTripRepository.saveAndFlush(airTrip);
        telegramBot.sendMessageToAllRegisteredUsers(AirTripMapper.entityToTelegramDto(entity));
    }

    @Override
    @Transactional
    public void update(AirTripData data, MultipartFile file, Long id) throws IOException {
       var airTrip = airTripRepository.findById(id)
               .orElseThrow(() -> new AirTripNotFoundException("Air trip with "+ id + " was not found"));
        var plane = planeRepository.findById(data.getPlaneId())
                .orElseThrow(() -> new PlaneNotFoundException("Plane with " + data.getPlaneId() + " was not found"));
        var fromCountry = countryRepository.findById(data.getFromCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country with " + data.getFromCountryId() + " was not found" ));
        var toCountry = countryRepository.findById(data.getToCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country with " + data.getToCountryId() + " was not found" ));
        if(!file.isEmpty()){
            airTrip.setImageOfAirCompany(file.getBytes());
        }
        airTrip.setPlane(plane);
        airTrip.setEnabled(data.isEnabled());
        airTrip.setPrice(data.getPrice());
        airTrip.setDepartureDate(data.getDepartureDate());
        airTrip.setToCountry(toCountry);
        airTrip.setFromCountry(fromCountry);

    }

    @Override
    public List<AirTripDTO> findAll() {
        return airTripRepository.findAll()
                .stream()
                .map(e-> {
                    try {
                        var elem =  AirTripMapper.entityToDto(e);
                        if(elem.getDepartureDate().isBefore(LocalDateTime.now())){
                            elem.setEnabled(false);
                        }
                        return elem;
                    }
                    catch (Exception exception){
                        System.out.println("Message " + exception.getMessage());
                        return null;
                    }
                })
                .toList();
    }

    @Override
    public void delete(Long id) {
        var airTrip = airTripRepository.findById(id)
                .orElseThrow(() -> new AirTripNotFoundException("Air trip with "+ id + " was not found"));
        if(!airTrip.isEnabled())
            airTripRepository.delete(airTrip);
    }
}
