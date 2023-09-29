package com.example.airtrip.elasticsearch.controller;


import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.domain.data.dataforrestapi.PlaneData;
import com.example.airtrip.domain.enums.Colour;
import com.example.airtrip.elasticsearch.elasticentity.CountryElastic;
import com.example.airtrip.elasticsearch.elasticentity.PlaneElastic;
import com.example.airtrip.elasticsearch.elasticrepository.PlaneElasticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/elastic-plane")
@RequiredArgsConstructor
public class PlaneElasticController {
    private final PlaneElasticRepository planeElasticRepository;
    @PostMapping("/create")
    public ResponseEntity<?> createPlane(@RequestPart("data") PlaneData planeData,
                                           @RequestPart("file") MultipartFile file) {
        try {
            var id = UUID.randomUUID().toString();
            var plane = PlaneElastic.builder()
                    .name(planeData.getName())
                    .colour(planeData.getColour())
                    .id(id)
                    .build();
            if(file!=null && file.getBytes().length!=0){
                plane.setImageFile(file.getBytes());
            }
            plane = planeElasticRepository.save(plane);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(plane);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAllPlane() {
        try {
            var planes = planeElasticRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(planes);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @DeleteMapping ("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") String id){
        try {
            planeElasticRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Delete was successfully");
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage() +
                            " Delete was unsuccessfully");
        }
    }
    @GetMapping("/paging")
    public ResponseEntity<?> page(@RequestParam("page") Long page,
                                  @RequestParam("size") Long size){
        try{
            var planes = planeElasticRepository.findAll(PageRequest.of(
                    Math.toIntExact(page),
                    Math.toIntExact(size)));
            return ResponseEntity.status(HttpStatus.OK)
                    .body(planes);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/findByNameAndColour")
    public ResponseEntity<?> findNameLikeAndByColour(@RequestParam("colour") Colour colour,
                                                     @RequestParam("name") String name){
        try{
            var planes = planeElasticRepository.findPlaneElasticByColourAndNameLike(colour,
                    "%" + name + "%");
            return ResponseEntity.status(HttpStatus.OK)
                    .body(planes);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/findByColour")
    public ResponseEntity<?> findByColour(@RequestParam("colour") Colour colour){
        try{
            var planes = planeElasticRepository.findPlaneElasticByColour(colour);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(planes);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
    @GetMapping("/findByNameContains")
    public ResponseEntity<?> findNameContains(@RequestParam("part") String part){
        try{
            var planes = planeElasticRepository.findPlaneElasticsByNameContainsIgnoreCase(part);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(planes);
        }
        catch (Exception exception){
            log.error(exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(exception.getMessage());
        }
    }
}
