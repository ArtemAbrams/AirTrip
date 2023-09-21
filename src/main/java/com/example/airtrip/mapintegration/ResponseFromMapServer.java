package com.example.airtrip.mapintegration;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;





/*{
        "results": [
        {
        "address_components": [
        {
        "long_name": "\u0406\u0442\u0430\u043B\u0456\u044F",
        "short_name": "IT",
        "types": [
        "country",
        "political"
        ]
        }
        ],
        "formatted_address": "\u0406\u0442\u0430\u043B\u0456\u044F",
        "geometry": {
        "bounds": {
        "northeast": {
        "lat": 47.092,
        "lng": 18.7975999
        },
        "southwest": {
        "lat": 35.4897,
        "lng": 6.6267201
        }
        },
        "location": {
        "lat": 41.87194,
        "lng": 12.56738
        },
        "location_type": "APPROXIMATE",
        "viewport": {
        "northeast": {
        "lat": 47.092,
        "lng": 18.7975999
        },
        "southwest": {
        "lat": 35.4897,
        "lng": 6.6267201
        }
        }
        },
        "place_id": "ChIJA9KNRIL-1BIRb15jJFz1LOI",
        "types": [
        "country",
        "political"
        ]
        }
        ],
        "status": "OK"
        }*/

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFromMapServer {
   @JsonProperty("results")
   private Result[] results;
   private String status;
}
