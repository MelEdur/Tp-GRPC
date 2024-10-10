package com.tpkafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tpkafka.entity.OrdenDeDespacho;
import com.tpkafka.entity.RecepcionResponse;
import com.tpkafka.repository.IOrdenDeDespachoRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProveedorService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final IOrdenDeDespachoRepository _ordenDeDespachoRepository;

    public void enviarDespacho(/*PARAMETROS?*/){
        //TO-DO

        //EJEMPLO PARA MANDAR ALGO AL TOPIC
        //kafkaTemplate.send("topic a enviar","mensaje a enviar");
    }


    //SE EJECUTA CUANDO RECIBE ALGO POR EL TOPIC DE /recepcion
    @KafkaListener(topics = "_recepcion", groupId = "default")
    public void recibirRecepcion(String mensajeRecepcion){
        
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            // Deserialize JSON string into MyData object
            RecepcionResponse response = objectMapper.readValue(mensajeRecepcion, RecepcionResponse.class);
            OrdenDeDespacho ordenDeDespachoModificable =  _ordenDeDespachoRepository.findById(response.getIdOrdenDeDespacho()).get();
            ordenDeDespachoModificable.setFechaRecepcion(response.getFechaDeRecepcion());
            _ordenDeDespachoRepository.save(ordenDeDespachoModificable);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void enviarNovedades(/*PARAMETROS?*/){
        //TO-DO

        //PARA ENVIAR A UN TOPIC
        //kafkaTemplate.send("topic a enviar","mensaje");
    }
}
