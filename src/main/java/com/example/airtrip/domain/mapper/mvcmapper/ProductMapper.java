package com.example.airtrip.domain.mapper.mvcmapper;


import com.example.airtrip.domain.dto.dtoformvc.ConflictCountryDTO;
import com.example.airtrip.domain.dto.dtoformvc.ProductDTO;
import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import com.example.airtrip.domain.entity.entityformvc.Product;
import lombok.experimental.UtilityClass;

import java.util.List;

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
    public List<ProductDTO> entityListToDTOList(List<Product> products){
        return products.stream()
                .map(ProductMapper::entityToDTO)
                .toList();
    }
}
