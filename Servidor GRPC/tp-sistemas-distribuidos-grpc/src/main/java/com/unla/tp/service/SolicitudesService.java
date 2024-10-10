package com.unla.tp.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.tp.entity.Item;
import com.unla.tp.entity.OrdenDTO;
import com.unla.tp.entity.OrdenDeCompra;
import com.unla.tp.entity.OrdenDeDespacho;
import com.unla.tp.entity.RecepcionDTO;
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


    public void enviarRecepcion(int idOrdenDeDespacho, LocalDate fechaDeRecepcion){
        String topic = ("_recepcion");
        RecepcionDTO recepcionMensaje = new RecepcionDTO(idOrdenDeDespacho, fechaDeRecepcion);
 
        try {
            kafkaTemplate.send(topic,objectMapper.writeValueAsString(recepcionMensaje));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    public void enviarOrden(String codigo, List<Item> items){
        KafkaTopicService topicService = new KafkaTopicService();
        KafkaConsumerService consumerService = new KafkaConsumerService();
        //Generar una orden
        OrdenDeCompra ordenDeCompraGenerada = OrdenDeCompra.builder()
                                                .estado("solicitada")
                                                .codigoTienda(codigo)
                                                .items(items)
                                                .fechaDeSolicitud(LocalDate.now()).build();
       //Guardar en BD
        OrdenDeCompra ordenDeCompra = ordenDeCompraRepository.save(ordenDeCompraGenerada);

       //Enviar al topic de _orden-de-compra un string con formato Json mapeando antes el codigo de tienda, idODC, items y fcha solicitud
        OrdenDTO ordenMensaje = new OrdenDTO(ordenDeCompra.getIdOrdenDeCompra(), ordenDeCompra.getCodigoTienda(),
                                                ordenDeCompra.getItems(),ordenDeCompra.getFechaDeSolicitud());
        
        String topicODC = "_orden-de-compra";

        try {
            kafkaTemplate.send(topicODC, objectMapper.writeValueAsString(ordenMensaje));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String topicSolicitud = "_"+codigo+"_solicitudes";
        topicService.crearTopic(topicSolicitud);

        String topicDespacho = "_"+codigo+"_despacho";
        topicService.crearTopic(topicDespacho);

        //Creo los consumers para solicitud y despacho
        consumerService.addConsumer(topicSolicitud);
        consumerService.addConsumer(topicDespacho);
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
