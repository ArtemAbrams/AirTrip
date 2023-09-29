package com.example.airtrip.elasticsearch.elasticrepository;

import com.example.airtrip.elasticsearch.elasticentity.AirTripElastic;
import com.example.airtrip.elasticsearch.elasticentity.CountryElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AirTripElasticRepository extends ElasticsearchRepository<AirTripElastic, String> {
}
