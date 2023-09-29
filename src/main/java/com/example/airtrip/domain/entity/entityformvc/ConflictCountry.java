package com.example.airtrip.domain.entity.entityformvc;

import com.example.airtrip.domain.entity.BasicEntity;
import com.example.airtrip.domain.enums.TypeOfConflict;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class ConflictCountry extends BasicEntity {
    private String name;
    @Lob
    @Column(length = 2147483647)
    private String flag;
    @Enumerated(value = EnumType.STRING)
    private TypeOfConflict typeOfConflict;
    @OneToMany(mappedBy = "conflictCountry", cascade = {CascadeType.ALL})
    private List<OrderM> orders;
}
