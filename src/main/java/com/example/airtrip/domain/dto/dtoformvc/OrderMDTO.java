package com.example.airtrip.domain.dto.dtoformvc;



import com.example.airtrip.domain.entity.entityformvc.ConflictCountry;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderMDTO {
    private Long id;
    private String companyName;
    private byte[] imageOfCompany;
    private String countryName;
    private byte[] countryFlag;

    public String generateBase64CompanyImage() {
        return Base64.encodeBase64String(imageOfCompany);
    }
    public String generateBase64FlagImage() {
        return Base64.encodeBase64String(countryFlag);
    }
}
