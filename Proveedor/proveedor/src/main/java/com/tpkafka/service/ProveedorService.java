package com.tpkafka.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProveedorService {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void enviarDespacho(/*PARAMETROS?*/){
        //TO-DO

        //EJEMPLO PARA MANDAR ALGO AL TOPIC
        //kafkaTemplate.send("topic a enviar","mensaje a enviar");
    }


    //SE EJECUTA CUANDO RECIBE ALGO POR EL TOPIC DE /recepcion
    @KafkaListener(topics = "_recepcion", groupId = "default")
    public void recibirRecepcion(String message){
        //TO-DO
    }

    public void enviarNovedades(/*PARAMETROS?*/){
        //TO-DO

        //PARA ENVIAR A UN TOPIC
        //kafkaTemplate.send("topic a enviar","mensaje");
    }
}
