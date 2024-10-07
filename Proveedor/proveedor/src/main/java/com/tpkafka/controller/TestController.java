package com.tpkafka.controller;

import com.tpkafka.entity.Item;
import com.tpkafka.entity.Stock;
import com.tpkafka.repository.IStockRepository;
import com.tpkafka.service.OrdenesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final OrdenesService ordenesService;
    private final IStockRepository stockRepository;

    @GetMapping("/enviar")
    public void enviar(){
        List<Item> pedido = List.of(
                Item.builder().codigo("AAA").color("ROJO").talle("XL").cantidad(5).build(),
                Item.builder().codigo("AAB").color("ROJO").talle("XL").cantidad(5).build(),
                Item.builder().codigo("AAC").color("ROJO").talle("XL").cantidad(5).build(),
                Item.builder().codigo("AAD").color("ROJO").talle("XL").cantidad(5).build(),
                Item.builder().codigo("AAE").color("ROJO").talle("XL").cantidad(5).build(),
                Item.builder().codigo("AAF").color("ROJO").talle("XL").cantidad(5).build()
                );

        ordenesService.procesarOrden(pedido,"A01",1,LocalDate.now());
    }

    @GetMapping("/cargar")
    public void cargar(){
        List<Stock> pedido = List.of(
                Stock.builder().codigo("AAA").cantidad(10).build(),
                Stock.builder().codigo("AAB").cantidad(10).build(),
                Stock.builder().codigo("AAC").cantidad(10).build(),
                Stock.builder().codigo("AAD").cantidad(10).build(),
                Stock.builder().codigo("AAE").cantidad(10).build(),
                Stock.builder().codigo("AAF").cantidad(10).build());

       stockRepository.saveAll(pedido);
    }

}
