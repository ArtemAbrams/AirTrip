package com.example.airtrip.domain.mapper.mvcmapper;


import com.example.airtrip.domain.dto.dtoformvc.ConflictCountryDTO;
import com.example.airtrip.domain.dto.dtoformvc.ProductDTO;
import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import com.example.airtrip.domain.entity.entityformvc.Product;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ProductMapper {
    public ProductDTO entityToDTO(Product product){
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .typeOfWeapon(product.getTypeOfWeapon())
                .image(product.getImage())
                .build();

    }
}
