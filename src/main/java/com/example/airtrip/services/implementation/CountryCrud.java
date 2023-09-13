package com.example.airtrip.services.implementation;

import com.example.airtrip.domain.data.dataforrestapi.CountryData;
import com.example.airtrip.domain.dto.dtoforrestapi.CountryDTO;
import com.example.airtrip.domain.mapper.restapimapper.CountryMapper;
import com.example.airtrip.domain.mapper.restapimapper.PlaneMapper;
import com.example.airtrip.exception.CountryNotFoundException;
import com.example.airtrip.exception.HasTripToCountryException;
import com.example.airtrip.exception.PlaneNotFoundException;
import com.example.airtrip.repository.CountryRepository;
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
public class CountryCrud implements CrudOperations<CountryData, CountryDTO> {
    private final CountryRepository countryRepository;
    @Override
    public CountryDTO create(CountryData data, MultipartFile file) throws IOException {
       var entity = CountryMapper.dataToEntity(data, file.getBytes());
       countryRepository.saveAndFlush(entity);
       return CountryMapper.entityToDto(entity);
    }

    @Override
    @Transactional
    public CountryDTO update(CountryData data, MultipartFile file, Long id) throws IOException {
      var entity = countryRepository.findById(id)
              .orElseThrow(()-> new CountryNotFoundException("Country with " + id + " was not found"));
      if(!file.isEmpty() && file.getBytes().length!=0){
          entity.setImageFile(file.getBytes());
      }
      entity.setName(data.getName());
      return CountryMapper.entityToDto(entity);
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

    @Override
    public Page<CountryDTO> findAll(Long page, Long size) {
        return countryRepository.findAll(PageRequest.of(Math.toIntExact(page), Math.toIntExact(size)))
                .map(e -> {
                    try {
                        return CountryMapper.entityToDto(e);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    @Override
    public CountryDTO getById(Long Id) throws IOException {
        return CountryMapper.entityToDto(
               countryRepository.findById(Id)
                        .orElseThrow(() -> new PlaneNotFoundException("Country with id " + Id + " was not found")));
    }
}
