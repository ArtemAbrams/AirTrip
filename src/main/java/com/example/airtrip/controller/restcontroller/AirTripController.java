package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.AirTripData;
import com.example.airtrip.domain.dto.dtoforrestapi.AirTripDTO;
import com.example.airtrip.domain.dto.dtoforrestapi.ResponseDTO;
import com.example.airtrip.services.implementation.AirTripCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airTrip")
@Slf4j
public class AirTripController {
    private final AirTripCrud airTripCrud;
    @PostMapping("/create")
    public ResponseDTO<?> createCountry(@RequestPart("data") AirTripData data,
                                             @RequestPart("file") MultipartFile file){
        try {
            return ResponseDTO.data(airTripCrud.create(data, file),
                    HttpStatus.OK);

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/update")
    public ResponseDTO<?> updateCountry(@RequestPart("data") AirTripData data,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart Long id){
        try {
            return ResponseDTO.data(airTripCrud.update(data, file, id),
                    HttpStatus.OK);

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getAll")
    public ResponseDTO<?> findAll(){
        try {
            return ResponseDTO.data(airTripCrud.findAll(), HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAirTripById")
    public ResponseDTO<?> getById(@RequestParam Long id){
        try {
            return ResponseDTO.data(airTripCrud.getById(id), HttpStatus.OK);
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/pageFind")
    public ResponseDTO<?> getAll(@RequestParam Long page,
                                  @RequestParam Long size){
        try {
            return ResponseDTO.data(airTripCrud.findAll(page, size),
                    HttpStatus.OK);
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete")
    public ResponseDTO<?> delete(@RequestParam Long id){
        try {
            airTripCrud.delete(id);
            return ResponseDTO.success();

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
