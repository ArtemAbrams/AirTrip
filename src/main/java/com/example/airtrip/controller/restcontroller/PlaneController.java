package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.PlaneData;
import com.example.airtrip.services.implementation.PlaneCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plane")
@Slf4j
public class PlaneController {
    private final PlaneCrud planeCrud;
    @PostMapping("/create")
    public ResponseEntity<?> createPlane(@RequestPart("data") PlaneData planeData,
                                                @RequestPart("file") MultipartFile file){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(planeCrud.create(planeData, file));
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPlane(@RequestPart("data") PlaneData planeData,
                                              @RequestPart("file") MultipartFile file,
                                              @RequestPart Long id) throws IOException {
            try {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(planeCrud.update(planeData, file, id));
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
        try{
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(planeCrud.findAll());
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        try {
            planeCrud.delete(id);
            return ResponseEntity.ok(HttpStatus.OK);

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping ("/getPlaneById")
    public ResponseEntity<?> getById(@RequestParam Long id){
        try {
           return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(planeCrud.getById(id));
        }
        catch (Exception exception){
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
                    .body(planeCrud.findAll(page, size));
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
}
