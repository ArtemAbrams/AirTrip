package com.example.airtrip.domain.dto.dtoformvc;



import com.example.airtrip.domain.enums.TypeOfConflict;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConflictCountryDTO {
    private Long id;
    private String name;
    private byte[] flag;
    private TypeOfConflict typeOfConflict;
    public String generateBase64Image() {
        return Base64.encodeBase64String(getFlag());
    }
}
