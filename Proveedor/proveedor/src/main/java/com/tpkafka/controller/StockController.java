package com.tpkafka.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tpkafka.entity.Stock;
import com.tpkafka.service.StockService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @CrossOrigin("http://localhost:8001")
    @GetMapping("/stocks")
    public List<Stock> traerStocks(){
        return stockService.traerTodos();
    }

    @CrossOrigin("http://localhost:8001")
    @PutMapping("/stocks")
    public int modificarStock(@RequestBody Stock stock){
        return stockService.modificarStock(stock);
    }

    @CrossOrigin("http://localhost:8001")
    @PostMapping("/crearProducto")
    public void crearProducto(@RequestBody Stock nuevoStock){
        stockService.agregarProducto(nuevoStock);
    }
}
