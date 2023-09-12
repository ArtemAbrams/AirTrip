package com.example.airtrip.domain.mapper.mvcmapper;

import com.example.airtrip.domain.dto.dtoformvc.OrderMDTO;
import com.example.airtrip.domain.entity.entityformvc.OrderM;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMMapper {
    public OrderMDTO entityToDTO(OrderM orderM){
        return OrderMDTO.builder()
                .id(orderM.getId())
                .companyName(orderM.getCompanyName())
                .imageOfCompany(orderM.getImageOfCompany())
                .countryName(orderM.getCompanyName())
                .countryFlag(orderM.getImageOfCompany())
                .build();
    }
}
