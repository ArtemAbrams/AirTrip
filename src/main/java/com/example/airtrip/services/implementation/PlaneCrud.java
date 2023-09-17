package com.example.airtrip.services.implementation;

import com.example.airtrip.domain.data.dataforrestapi.PlaneData;
import com.example.airtrip.domain.dto.dtoforrestapi.PlaneDTO;
import com.example.airtrip.domain.mapper.restapimapper.AirTripMapper;
import com.example.airtrip.domain.mapper.restapimapper.PlaneMapper;
import com.example.airtrip.exception.AirTripNotFoundException;
import com.example.airtrip.exception.PlaneHasTripException;
import com.example.airtrip.exception.PlaneNotFoundException;
import com.example.airtrip.repository.PlaneRepository;
import com.example.airtrip.services.CrudOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaneCrud implements CrudOperations<PlaneData, PlaneDTO> {
    private final PlaneRepository planeRepository;

    @Override
    public PlaneDTO create(PlaneData data, MultipartFile file) throws IOException {
       var entity = PlaneMapper.dataToEntity(data, file.getBytes());
       planeRepository.saveAndFlush(entity);
       return PlaneMapper.entityToDto(entity);
    }

    @Override
    @Transactional
    public PlaneDTO update(PlaneData data, MultipartFile file, Long id) throws IOException {
      var plane = planeRepository.findById(id)
              .orElseThrow(() -> new PlaneNotFoundException("Plane with " + id + " was not found"));
      if(!file.isEmpty() && file.getBytes().length!=0){
          plane.setImageFile(file.getBytes());
      }
      plane.setName(data.getName());
      plane.setColour(data.getColour());
        return PlaneMapper.entityToDto(plane);
    }

    @Override
    public List<PlaneDTO> findAll() {
        return planeRepository.findAll()
                .stream()
                .map(e-> {
                    try {
                        return PlaneMapper.entityToDto(e);
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
        var plane = planeRepository.findById(id)
                .orElseThrow(()-> new PlaneNotFoundException("Plane with " + id + " was not found"));
        if(plane.getAirTripList().isEmpty())
           planeRepository.delete(plane);
        else
            throw new PlaneHasTripException("You cannot do this action because this plane has trips");
    }

    @Override
    public Page<PlaneDTO> findAll(Long page, Long size) {
         return planeRepository.findAll(PageRequest.of(Math.toIntExact(page), Math.toIntExact(size)))
                .map(e -> {
                    try {
                        return PlaneMapper.entityToDto(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    @Override
    public PlaneDTO getById(Long Id) throws IOException {
        return PlaneMapper.entityToDto(
               planeRepository.findById(Id)
                        .orElseThrow(() -> new PlaneNotFoundException("Plane     with id " + Id + " was not found")));
    }
}
