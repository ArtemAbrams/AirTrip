package com.example.airtrip.services.implementation;

import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.domain.dto.dtoforrestapi.CountryDTO;
import com.example.airtrip.domain.mapper.restapimapper.CountryMapper;
import com.example.airtrip.exception.CountryNotFoundException;
import com.example.airtrip.exception.HasTripToCountryException;
import com.example.airtrip.repository.CountryRepository;
import com.example.airtrip.services.CrudOperations;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryCrud implements CrudOperations<CountryData, CountryDTO> {
    private final CountryRepository countryRepository;
    @Override
    public void create(CountryData data, MultipartFile file) throws IOException {
       var entity = CountryMapper.dataToEntity(data, file.getBytes());
       countryRepository.saveAndFlush(entity);
    }

    @Override
    @Transactional
    public void update(CountryData data, MultipartFile file, Long id) throws IOException {
      var entity = countryRepository.findById(id)
              .orElseThrow(()-> new CountryNotFoundException("Country with " + id + " was not found"));
      if(!file.isEmpty()){
          entity.setImageFile(file.getBytes());
      }
      entity.setName(data.getName());
    }

    @Override
    public List<CountryDTO> findAll() {
            return countryRepository.findAll()
                    .stream()
                    .map(e -> {
                        try {
                           return CountryMapper.entityToDto(e);
                        }
                        catch (Exception exception){
                            System.out.println("Exception  " + exception.getMessage());
                           return null;
                        }
                    })
                    .toList();
    }

    @Override
    public void delete(Long id) {
            var entity = countryRepository.findById(id)
                    .orElseThrow(()-> new CountryNotFoundException("Country with " + id + " was not found" ));
            if(entity.getFinalRoutes().isEmpty() && entity.getInitialRoutes().isEmpty())
                countryRepository.delete(entity);
            else
                throw new HasTripToCountryException("Have trips to this country");
    }
}
