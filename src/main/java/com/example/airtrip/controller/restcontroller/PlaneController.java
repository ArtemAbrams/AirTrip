package com.example.airtrip.controller.restcontroller;

import com.example.airtrip.domain.data.dataforrestapi.PlaneData;
import com.example.airtrip.domain.dto.dtoforrestapi.PlaneDTO;
import com.example.airtrip.domain.dto.dtoforrestapi.ResponseDTO;
import com.example.airtrip.services.implementation.PlaneCrud;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public ResponseDTO<?> createPlane(@RequestPart("data") PlaneData planeData,
                                                @RequestPart("file") MultipartFile file){
        try {
            return ResponseDTO.data( planeCrud.create(planeData, file),
                    HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDTO<?> createPlane(@RequestPart("data") PlaneData planeData,
                                              @RequestPart("file") MultipartFile file,
                                              @RequestPart Long id) throws IOException {
            try {
                return ResponseDTO.data(planeCrud.update(planeData, file, id),
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
        try{
            return ResponseDTO.data(planeCrud.findAll(),
                    HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/delete")
    public ResponseDTO<?> delete(@RequestParam Long id){
        try {
            planeCrud.delete(id);
            return ResponseDTO.success();

        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping ("/getPlaneById")
    public ResponseDTO<?> getById(@RequestParam Long id){
        try {
            return ResponseDTO.data(planeCrud.getById(id),
                    HttpStatus.OK);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/pageFind")
    public ResponseDTO<?> getAll(@RequestParam Long page,
                                 @RequestParam Long size){
        try {
            return ResponseDTO.data(planeCrud.findAll(page, size),
                    HttpStatus.OK);
        }
        catch (Exception exception) {
            log.error(exception.getMessage());
            return ResponseDTO.error(exception.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
