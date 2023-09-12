package com.example.airtrip.domain.dto.dtoformvc;

import com.example.airtrip.domain.enums.TypeOfWeapon;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private TypeOfWeapon typeOfWeapon;
    private byte[] image;
    public String generateBase64Image() {
        return Base64.encodeBase64String(image);
    }
}
