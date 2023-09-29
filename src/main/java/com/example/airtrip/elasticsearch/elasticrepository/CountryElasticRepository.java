package com.example.airtrip.elasticsearch.elasticrepository;

import com.example.airtrip.elasticsearch.elasticentity.CountryElastic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CountryElasticRepository extends ElasticsearchRepository<CountryElastic, String> {
    List<CountryElastic> findByNameLike(String prefix);
}
