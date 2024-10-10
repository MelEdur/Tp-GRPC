package com.tpkafka.controller;

import com.tpkafka.entity.Stock;
import com.tpkafka.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stocks")
    public List<Stock> traerStocks(){
        return stockService.traerTodos();
    }

    @PutMapping("/stocks")
    public int modificarStock(@RequestBody Stock stock){
        return stockService.modificarStock(stock);
    }
}
