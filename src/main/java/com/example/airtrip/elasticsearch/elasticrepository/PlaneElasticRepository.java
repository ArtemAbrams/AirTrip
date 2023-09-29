package com.example.airtrip.elasticsearch.elasticrepository;

import com.example.airtrip.domain.enums.Colour;
import com.example.airtrip.elasticsearch.elasticentity.PlaneElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PlaneElasticRepository extends ElasticsearchRepository<PlaneElastic, String> {
    List<PlaneElastic> findPlaneElasticByColourAndNameLike(Colour colour, String name);
    List<PlaneElastic> findPlaneElasticByColour(Colour colour);
    List<PlaneElastic> findPlaneElasticsByNameContainsIgnoreCase(String part);
}
