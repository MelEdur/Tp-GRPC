package com.unla.tp.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.tp.entity.OrdenDeCompra;
import com.unla.tp.entity.OrdenDeDespacho;
import com.unla.tp.repository.IOrdenDeCompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SolicitudesService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final IOrdenDeCompraRepository ordenDeCompraRepository;

    public void actualizarSolicitud(String codigoTienda, String mensaje){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(mensaje);

            OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findById(jsonNode.get("idOrdenDeCompra").asInt())
                    .orElseThrow(()-> new RuntimeException("No existe orden de compra con tal id"));

            ordenDeCompra.setEstado(jsonNode.get("estado").asText());
            ordenDeCompra.setObservaciones(jsonNode.get("observaciones").asText());

            ordenDeCompraRepository.save(ordenDeCompra);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void recibirDespacho(String codigoTienda, String mensaje){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            JsonNode jsonNode = objectMapper.readTree(mensaje);

            OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findById(jsonNode.get("idOrdenDeCompra").asInt())
                    .orElseThrow(()-> new RuntimeException("No existe orden de compra con tal id"));

            OrdenDeDespacho ordenDeDespacho = OrdenDeDespacho.builder()
                    .idOrdenDeCompra(jsonNode.get("idOrdenDeCompra").asInt())
                    .fechaEstimada(LocalDate.parse(jsonNode.get("fechaEstimada").asText()))
                    .build();

            ordenDeCompra.setOrdenDeDespacho(ordenDeDespacho);

            ordenDeCompraRepository.save(ordenDeCompra);
        }catch (Exception e){
            e.printStackTrace();
        }
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
