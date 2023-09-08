package com.example.airtrip.services.implementation;

import com.example.airtrip.domain.data.PlaneData;
import com.example.airtrip.domain.dto.PlaneDTO;
import com.example.airtrip.domain.mapper.PlaneMapper;
import com.example.airtrip.exception.PlaneNotFoundException;
import com.example.airtrip.repository.PlaneRepository;
import com.example.airtrip.services.CrudOperations;
import lombok.RequiredArgsConstructor;
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
    public void create(PlaneData data, MultipartFile file) throws IOException {
       var entity = PlaneMapper.dataToEntity(data, file.getBytes());
       planeRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void update(PlaneData data, MultipartFile file, Long id) throws IOException {
      var plane = planeRepository.findById(id)
              .orElseThrow(() -> new PlaneNotFoundException("Plane with " + id + " was not found"));
      if(!file.isEmpty()){
          plane.setImageFile(file.getBytes());
      }
      plane.setName(data.getName());
      plane.setColour(data.getColour());
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
        var entity = planeRepository.findById(id)
                .orElseThrow(()-> new PlaneNotFoundException("Plane with " + id + " was not found"));
        planeRepository.delete(entity);
    }
}
