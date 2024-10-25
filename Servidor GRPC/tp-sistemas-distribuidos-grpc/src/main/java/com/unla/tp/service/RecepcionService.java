package com.unla.tp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.unla.tp.entity.Item;
import com.unla.tp.entity.OrdenDTO;
import com.unla.tp.entity.OrdenDeCompra;
import com.unla.tp.entity.Topic;
import com.unla.tp.repository.IOrdenDeCompraRepository;
import com.unla.tp.repository.ITopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecepcionService {

    private final KafkaConsumerService consumerService;
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final IOrdenDeCompraRepository ordenDeCompraRepository;
    private final ObjectMapper objectMapper;
    private final ITopicRepository topicRepository;

    public void enviarOrden(String codigo, List<Item> items){
        KafkaTopicService topicService = new KafkaTopicService();
        //KafkaConsumerService consumerService = new KafkaConsumerService();
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
                items,ordenDeCompra.getFechaDeSolicitud());

        String topicODC = "_orden-de-compra";

        try {
            objectMapper.registerModule(new JavaTimeModule());
            kafkaTemplate.send(topicODC, objectMapper.writeValueAsString(ordenMensaje));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        String topicSolicitud = "_"+codigo+"_solicitudes";
        topicService.crearTopic(topicSolicitud);
        if(!topicRepository.existsByTopic(topicSolicitud)){
            topicRepository.save(Topic.builder().topic(topicSolicitud).build());
        }

        String topicDespacho = "_"+codigo+"_despacho";
        topicService.crearTopic(topicDespacho);
        topicRepository.save(Topic.builder().topic(topicDespacho).build());
        if(!topicRepository.existsByTopic(topicDespacho)){
            topicRepository.save(Topic.builder().topic(topicDespacho).build());
        }

        //Creo los consumers para solicitud y despacho
        consumerService.addConsumer(topicSolicitud);
        consumerService.addConsumer(topicDespacho);
    }
}
