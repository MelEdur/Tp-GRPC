package com.tpkafka.controller;

import com.tpkafka.entity.Stock;
import com.tpkafka.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @CrossOrigin("http://localhost:8001")
    @GetMapping("/stocks")
    public List<Stock> traerStocks(){
        return stockService.traerTodos();
    }

    @CrossOrigin("http://localhost:8000")
    @PutMapping("/stocks")
    public int modificarStock(@RequestBody Stock stock){
        return stockService.modificarStock(stock);
    }

    @PostMapping("/crearProducto")
    public void crearProducto(@RequestBody Stock nuevoStock){
        stockService.agregarProducto(nuevoStock);
    }
}
