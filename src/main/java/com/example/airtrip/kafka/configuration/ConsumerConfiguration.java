package com.example.airtrip.kafka.configuration;



import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;

@Configuration
public class ConsumerConfiguration {
    @Value("${spring.kafka.bootstrap-servers}")
    private String serverBootstrap;
    @Bean
    public ConsumerFactory<String, Long> createConsumerFactory(){
        var properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, serverBootstrap);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "json");
        return new DefaultKafkaConsumerFactory<>(properties);
    }
    @Bean
    public KafkaListenerContainerFactory<
                ConcurrentMessageListenerContainer<String, Long>> factory(){
        ConcurrentKafkaListenerContainerFactory<String, Long> containerFactory
                = new ConcurrentKafkaListenerContainerFactory<>();
        containerFactory.setConsumerFactory(createConsumerFactory());
        return containerFactory;
    }

}
