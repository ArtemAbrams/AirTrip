package com.example.airtrip.controller.mvccontroller;


import com.example.airtrip.blobstorage.service.implementation.AzurePhotoBlobStorageImpl;
import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import com.example.airtrip.domain.enums.TypeOfConflict;
import com.example.airtrip.domain.mapper.mvcmapper.ConflictCountryMapper;
import com.example.airtrip.repository.ConflictCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/conflict/country")
public class ConflictCountryController {
    private final ConflictCountryMapper conflictCountryMapper;
    private final ConflictCountryRepository countryRepository;
    private final AzurePhotoBlobStorageImpl azurePhotoBlobStorage;
    @GetMapping("/getAll")
    public String getAll(ModelMap map){
        var countries = countryRepository.findAll()
                .stream()
                .map(conflictCountryMapper::entityToDTO)
                .toList();
        map.addAttribute("listOfCountry", countries);
        return "index_country";
    }
    @GetMapping("/getCountryByName")
    public String getAllByName(String countryName, ModelMap map){
        var countries = countryRepository
                .findConflictCountriesByNameLike(countryName + "%")
                .stream()
                .map(conflictCountryMapper::entityToDTO)
                .toList();
        map.addAttribute("listOfCountry", countries);
        return "index_country";
    }
    @GetMapping("/create")
    public String create(ModelMap modelMap){
        modelMap.addAttribute("conflicts", TypeOfConflict.values());
        return "create_country";
    }
    @PostMapping("/create")
    public String submitCreate(@RequestParam("name") String name,
                               @RequestParam("typeOfConflict") TypeOfConflict typeOfConflict,
                               @RequestParam("flag") MultipartFile file) throws IOException {
        var path = azurePhotoBlobStorage.upload(file);
        var country = ConflictCountry.builder()
                .name(name)
                .typeOfConflict(typeOfConflict)
                .flag(path)
                .build();
        countryRepository.saveAndFlush(country);
        return "redirect:/conflict/country/getAll";
    }
    @PostMapping("/deleteCountry")
    public String deleteCountry(@RequestParam("id") Long id) {
        var country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country with id " + id + " was not found"));
        azurePhotoBlobStorage.delete(country.getFlag());
        countryRepository.delete(country);
        return "redirect:/conflict/country/getAll";
    }
    @GetMapping("/editCountry")
    public String editCountry(@RequestParam("id") Long id,
                              ModelMap modelMap) {
        var country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country with id " + id + " was not found"));
        var countryDTO = conflictCountryMapper.entityToDTO(country);
        modelMap.addAttribute("dto", countryDTO);
        modelMap.addAttribute("conflicts", TypeOfConflict.values());
        return "edit_country";
    }
    @Transactional
    @PostMapping("/updateCountry")
    public String submitEditCountry(@RequestParam("id") Long id,
                                    @RequestParam("name") String name,
                                    @RequestParam("typeOfConflict") TypeOfConflict typeOfConflict,
                                    @RequestParam("flag") MultipartFile multipartFile) throws IOException {
        var country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country with id " + id + " was not found"));
        country.setName(name);
        country.setTypeOfConflict(typeOfConflict);
        var string = azurePhotoBlobStorage.update(multipartFile, country.getFlag());
        country.setFlag(string);
        return "redirect:/conflict/country/getAll";
    }
}
