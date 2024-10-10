package com.unla.tp.controller;

import com.unla.tp.repository.IOrdenDeCompraRepository;
import com.unla.tp.service.KafkaConsumerService;
import com.unla.tp.service.KafkaTopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TestController {

    private final KafkaConsumerService kafkaConsumerService;
    private final IOrdenDeCompraRepository ordenDeCompraRepository;
    private final KafkaTopicService kafkaTopicService;
    @GetMapping("/test")
    public void CrearConsumer(){
        kafkaConsumerService.addConsumer("_A01_solicitudes");
        kafkaConsumerService.addConsumer("_A01_despacho");
    }

    @PostMapping("/creaOreden")
    public String crearOrden(){

        return "Todo Ok";
    }
}
