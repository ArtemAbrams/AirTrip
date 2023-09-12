package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.AirTripData;
import com.example.airtrip.domain.dto.dtoforrestapi.AirTripDTO;
import com.example.airtrip.services.implementation.AirTripCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<String> createCountry(@RequestPart("data") AirTripData data,
                                                @RequestPart("file") MultipartFile file){
        try {
            airTripCrud.create(data, file);
            return ResponseEntity.ok()
                    .body("OK");

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<String> createCountry(@RequestPart("data") AirTripData data,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart Long id){
        try {
            airTripCrud.update(data, file, id);
            return ResponseEntity.ok()
                    .body("OK");

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }

    }
    @GetMapping("/getAll")
    public ResponseEntity<List<AirTripDTO>> findAll(){
        return ResponseEntity.ok()
                .body(airTripCrud.findAll());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id){
        try {
            airTripCrud.delete(id);
            return ResponseEntity.ok()
                    .body("OK");

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body(exception.getMessage());
        }
    }
}
