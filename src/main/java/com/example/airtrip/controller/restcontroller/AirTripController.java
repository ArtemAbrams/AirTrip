package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.AirTripData;
import com.example.airtrip.services.implementation.AirTripCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/airTrip")
@Slf4j
public class AirTripController {
    private final AirTripCrud airTripCrud;
    @PostMapping("/create")
    public ResponseEntity<?> createCountry(@RequestPart("data") AirTripData data,
                                             @RequestPart("file") MultipartFile file){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                            .body(airTripCrud.create(data, file));

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateCountry(@RequestPart("data") AirTripData data,
                                                @RequestPart("file") MultipartFile file,
                                                @RequestPart Long id){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(airTripCrud.update(data, file, id));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }

    }
    @GetMapping("/getAll")
    public ResponseEntity<?> findAll(){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(airTripCrud.findAll());
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/getAirTripById")
    public ResponseEntity<?> getById(@RequestParam Long id){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(airTripCrud.getById(id));
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/pageFind")
    public ResponseEntity<?> getAll(@RequestParam Long page,
                                  @RequestParam Long size){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(airTripCrud.findAll(page, size));
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        try {
            airTripCrud.delete(id);
            return ResponseEntity
                    .ok(HttpStatus.OK);

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
}
