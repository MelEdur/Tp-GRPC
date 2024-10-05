package com.unla.tp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SolicitudesService {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public void actualizarSolicitud(String codigoTienda, String mensaje){
        //TO-DO
    }

    public void recibirDespacho(String codigoTienda, String mensaje){
        //TO-DO
    }


    public void enviarRecepcion(){
        //TO-DO

        //PARA ENVIAR A UN TOPIC
        //kafkaTemplate.send("topic a enviar","mensaje");
    }

    //SE EJECUTA CUANDO RECIBE ALGO POR EL TOPIC DE /novedades
    @KafkaListener(topics = "_novedades", groupId = "default")
    public void recibirNovedad(String message){
        
    }
}
