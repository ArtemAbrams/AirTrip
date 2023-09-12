package com.example.airtrip.domain.dto.dtoformvc;

import com.example.airtrip.domain.enums.TypeOfWeapon;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.web.bind.annotation.GetMapping;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ListProductsOfOrderDTO {
    private Long id;
    private String name;
    private TypeOfWeapon typeOfWeapon;
    private byte[] image;
    private int count;
    public String generateBase64Image() {
        return Base64.encodeBase64String(image);
    }
}
