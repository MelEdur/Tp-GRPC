package com.tpkafka.service;

import com.tpkafka.entity.Item;
import com.tpkafka.entity.OrdenPausada;
import com.tpkafka.entity.Stock;
import com.tpkafka.repository.IOrdenPausadaRepository;
import com.tpkafka.repository.IStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final IStockRepository stockRepository;
    private final IOrdenPausadaRepository ordenPausadaRepository;
    private final OrdenesService ordenesService;
    private final KafkaTemplate kafkaTemplate;

    public void actualizarStock(Stock stock){
        stockRepository.save(stock);

        List<OrdenPausada> ordenPausadas = ordenPausadaRepository.findByItemCodigo(stock.getCodigo());
        for(OrdenPausada ordenPausada : ordenPausadas){
            ordenesService.procesarOrden(ordenPausada.getItems(),ordenPausada.getCodigoTienda(),ordenPausada.getIdOrden(),ordenPausada.getFechaSolicitud());
        }
    }
}
