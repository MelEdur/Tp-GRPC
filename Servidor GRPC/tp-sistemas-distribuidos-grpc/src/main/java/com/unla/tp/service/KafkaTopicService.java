package com.unla.tp.service;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;

@Service
public class KafkaTopicService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;


    //Función para crear un topic con el nombre ingresado
    public void crearTopic(String topic){
        Properties props = new Properties();
        props.put("bootstrap.servers",bootstrapServers);
        try(AdminClient adminClient = AdminClient.create(props)) {
            NewTopic newTopic = new NewTopic(topic,2, (short) 1);
            adminClient.createTopics(Collections.singletonList(newTopic));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
