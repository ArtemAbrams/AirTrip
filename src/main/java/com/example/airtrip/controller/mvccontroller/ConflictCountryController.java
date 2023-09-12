package com.example.airtrip.controller.mvccontroller;


import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import com.example.airtrip.domain.enums.TypeOfConflict;
import com.example.airtrip.domain.mapper.mvcmapper.ConflictCountryMapper;
import com.example.airtrip.repository.ConflictCountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/conflict/country")
public class ConflictCountryController {
    private final ConflictCountryRepository countryRepository;
    @GetMapping("/getAll")
    public String getAll(ModelMap map){
        var countries = countryRepository.findAll()
                .stream()
                .map(ConflictCountryMapper::entityToDTO)
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
        var country = ConflictCountry.builder()
                .name(name)
                .typeOfConflict(typeOfConflict)
                .flag(file.getBytes())
                .build();
        countryRepository.saveAndFlush(country);
        return "redirect:/conflict/country/getAll";
    }
    @PostMapping("/deleteCountry")
    public String deleteCountry(@RequestParam("id") Long id) {
        var country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country with id " + id + " was not found"));
        countryRepository.delete(country);
        return "redirect:/conflict/country/getAll";
    }
    @GetMapping("/editCountry")
    public String editCountry(@RequestParam("id") Long id,
                              ModelMap modelMap) {
        var country = countryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Country with id " + id + " was not found"));
        var countryDTO = ConflictCountryMapper.entityToDTO(country);
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
        if(multipartFile.getBytes().length!=0){
            country.setFlag(multipartFile.getBytes());
        }
        return "redirect:/conflict/country/getAll";
    }
}
