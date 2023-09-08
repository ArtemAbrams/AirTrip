package com.example.airtrip.controller;

import com.example.airtrip.domain.data.PlaneData;
import com.example.airtrip.domain.dto.PlaneDTO;
import com.example.airtrip.services.implementation.PlaneCrud;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plane")
@Slf4j
public class PlaneController {
    private final PlaneCrud planeCrud;
    @PostMapping("/create")
    public ResponseEntity<String> createPlane(@RequestPart("data") PlaneData planeData,
                                                @RequestPart("file") MultipartFile file){
        try {
            planeCrud.create(planeData, file);
            return ResponseEntity.ok()
                    .body("OK");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body("ERROR");
        }
    }
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> createPlane(@RequestPart("data") PlaneData planeData,
                                              @RequestPart("file") MultipartFile file,
                                              @RequestPart Long id) throws IOException {
            try {
                planeCrud.update(planeData, file, id);
                return ResponseEntity.ok()
                        .body("OK");
            }
            catch (Exception ex){
                log.error(ex.getMessage());
                return ResponseEntity.badRequest()
                        .body("Error");
            }
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<PlaneDTO>> findAll(){
        return ResponseEntity.ok()
                .body(planeCrud.findAll());
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam Long id){
        try {
            planeCrud.delete(id);
            return ResponseEntity.ok()
                    .body("OK");

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.badRequest()
                    .body("ERROR");
        }
    }
}
