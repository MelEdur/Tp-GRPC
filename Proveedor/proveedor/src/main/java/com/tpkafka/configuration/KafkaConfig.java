package com.tpkafka.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public KafkaAdmin kafkaAdmin(){
        return new KafkaAdmin(Map.of("bootstrap.servers","localhost:9092"));
    }

    @Bean
    public NewTopic ordenDeCompra(){
        return TopicBuilder.name("_orden-de-compra")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic recepcion(){
        return TopicBuilder.name("_recepcion")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic novedades(){
        return TopicBuilder.name("_novedades")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public ObjectMapper conversor(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
