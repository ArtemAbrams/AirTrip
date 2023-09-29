package com.example.airtrip.elasticsearch.elasticentity;

import com.example.airtrip.domain.enums.Colour;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "planes")
public class PlaneElastic {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Keyword)
    private Colour colour;
    @Field(type = FieldType.Binary, name = "image_plane")
    private byte[] imageFile;
    @Field(type = FieldType.Nested)
    private List<AirTripElastic> airTripList;
}
