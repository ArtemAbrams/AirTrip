package com.example.airtrip.kafka.configuration;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic deletePlaneTopic() {
        return TopicBuilder
                .name("deletePlane")
                .build();
    }

    @Bean
    public NewTopic deleteCountryTopic() {
        return TopicBuilder
                .name("deleteCountry")
                .build();
    }

    @Bean
    public NewTopic deleteAirTripTopic() {
        return TopicBuilder
                .name("deleteAirTrip")
                .build();
    }

}
