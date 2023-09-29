package com.example.airtrip.elasticsearch.elasticentity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "countries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryElastic {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Binary, name = "flag_country")
    private byte[] imageFile;
    @Field(type = FieldType.Nested)
    private List<AirTripElastic> initialRoutes;

    @Field(type = FieldType.Nested)
    private List<AirTripElastic> finalRoutes;
}
