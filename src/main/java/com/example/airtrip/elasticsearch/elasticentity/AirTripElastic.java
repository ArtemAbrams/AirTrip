package com.example.airtrip.elasticsearch.elasticentity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
@Document(indexName = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AirTripElastic {
    @Id
    private String id;

    @Field(type = FieldType.Double)
    private double price;

    @Field(type = FieldType.Boolean)
    private boolean enabled;

    @Field(type = FieldType.Date, format = DateFormat.basic_date_time_no_millis, pattern = "dd MMMM yyyy hh:mm:ss")
    private LocalDateTime departureDate;

    @Field(type = FieldType.Binary, name = "image_company")
    private byte[] imageOfAirCompany;

}
