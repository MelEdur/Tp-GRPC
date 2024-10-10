package com.unla.tp.service;

import java.time.LocalDate;
import java.util.List;

import com.unla.tp.entity.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.tp.repository.IOrdenDeCompraRepository;
import com.unla.tp.repository.IStockProveedorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SolicitudesService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final IOrdenDeCompraRepository ordenDeCompraRepository;
    private final ObjectMapper objectMapper;
    private final IStockProveedorRepository stockProveedorRepository;



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
            OrdenDeDespachoResponse ordenDeDespachoResponse = objectMapper.readValue(mensaje,OrdenDeDespachoResponse.class);
            System.out.println(ordenDeDespachoResponse.toString());

            OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.findById(ordenDeDespachoResponse.getIdOrdenDeCompra())
                    .orElseThrow(()-> new RuntimeException("No existe orden de compra con tal id"));

            OrdenDeDespacho ordenDeDespacho = OrdenDeDespacho.builder()
                    .idOrdenDeCompra(ordenDeDespachoResponse.getIdOrdenDeCompra())
                    .fechaEstimada(ordenDeDespachoResponse.getFechaEstimada())
                    .build();

            ordenDeCompra.setOrdenDeDespacho(ordenDeDespacho);

            ordenDeCompraRepository.save(ordenDeCompra);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void enviarRecepcion(int idOrdenDeDespacho, LocalDate fechaDeRecepcion){
        String topic = ("_recepcion");
        RecepcionDTO recepcionMensaje = new RecepcionDTO(idOrdenDeDespacho, fechaDeRecepcion);
 
        try {
            kafkaTemplate.send(topic,objectMapper.writeValueAsString(recepcionMensaje));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //SE EJECUTA CUANDO RECIBE ALGO POR EL TOPIC DE /novedades
    @KafkaListener(topics = "_novedades", groupId = "default")
    public void recibirNovedad(String message){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StockProveedor stock = objectMapper.readValue(message, StockProveedor.class);
            stockProveedorRepository.save(stock);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
