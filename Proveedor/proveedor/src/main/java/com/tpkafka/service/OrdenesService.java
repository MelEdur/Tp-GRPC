package com.tpkafka.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tpkafka.entity.*;
import com.tpkafka.repository.IOrdenDeDespachoRepository;
import com.tpkafka.repository.IOrdenPausadaRepository;
import com.tpkafka.repository.IStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrdenesService {

    private final KafkaTemplate<String,String> kafkaTemplate;
    private final IStockRepository stockRepository;
    private final IOrdenPausadaRepository ordenPausadaRepository;
    private final IOrdenDeDespachoRepository ordenDeDespachoRepository;
    private final ObjectMapper objectMapper;

    public void procesarOrden(List<Item> items, String codigoTienda, int idOrden, LocalDate fechaSolicitud){


        String observaciones = "";
        String estado = "ACEPTADO";

        List<Stock> stocks = stockRepository.findAll();

        for (int i= 0; i< items.size();i++){
            int index = -1;

            //REVISIÓN DE CÓDIGO
            for(Stock stock: stocks){
                if(stock.getCodigo().equals(items.get(i).getCodigo())){
                    index = i+1;
                    break;
                }
            }

            if (index != -1){
                //CODIGO EXISTE
                if(!(items.get(i).getCantidad() > 0)){
                    //CANTIDAD INCORRECTA
                    estado = "RECHAZADO";
                    observaciones += ("Articulo "+ items.get(i).getCodigo()+ ": cantidad mal informada");
                }else if(items.get(i).getCantidad()> stocks.get(index).getCantidad()){
                    //CANTIDAD CORRECTA PERO SIN STOCK
                    //System.out.println("\n\nStock en tienda:" + stocks.get(index+1).getCantidad()+"\n\nNombre Stock:" + stocks.get(index+1).getCodigo()+ "\n\n\nCantidad pedida:"+ items.get(i).getCantidad());
                    observaciones +=("Articulo "+ items.get(i).getCodigo()+ ": sin stock");
                }else {
                    //CANTIDAD CORRECTA Y CON STOCK
                    stocks.get(index).setCantidad(stocks.get(index).getCantidad()-items.get(i).getCantidad());
                }

            }else {
                //CODIGO NO EXISTE
                estado = "RECHAZADO";
                observaciones+=("Articulo "+ items.get(i).getCodigo()+ ": no existe, ");
            }
        }

        //CASO: STOCK INSUFICIENTE
        if(estado.equals("ACEPTADO") && !observaciones.equals("")){

            List<Item> itemsAux = new ArrayList<>();

            for(Item item : items){
                itemsAux.add(Item.builder()
                                .codigo(item.getCodigo())
                                .color(item.getColor())
                                .talle(item.getTalle())
                                .cantidad(item.getCantidad())
                        .build());
            }
            System.out.println("\n\n\nEstado:" + estado + "\nObservaciones:"+observaciones);
            ordenPausadaRepository.save(OrdenPausada.builder()
                    .codigoTienda(codigoTienda)
                    .fechaSolicitud(fechaSolicitud)
                    .idOrden(idOrden)
                    .items(itemsAux)
                    .build());
        }

        //CASO: HAY STOCK, CÓDIGOS BIEN, CANTIDADES BIEN
        if(estado.equals("ACEPTADO") && observaciones.equals("")){

            System.out.println("\n\n\n\nTODO SALIO BIEN");
            enviarOrdenDeDespacho(idOrden,codigoTienda);
            //Restar stocks
            stockRepository.saveAll(stocks);
        }

        //ENVÍO DE ESTADO Y OBSERVACIONES
        actualizarSolicitud(idOrden,codigoTienda,estado,observaciones);
    }

    public void enviarOrdenDeDespacho(int idOrdenDeCompra, String codigoTienda){
        LocalDate fechaEstimada = LocalDate.now().plusDays(1);

        int idOrdenDeDespacho = ordenDeDespachoRepository.save(OrdenDeDespacho.builder()
                .idOrdenDeCompra(idOrdenDeCompra)
                .fechaEstimada(fechaEstimada)
                .build()).getIdOrdenDeDespacho();

        DespachoResponse despachoResponse = new DespachoResponse(idOrdenDeCompra,idOrdenDeDespacho,fechaEstimada);
        String topic = ("_"+codigoTienda+"_despacho");
        try {
            kafkaTemplate.send(topic,objectMapper.writeValueAsString(despachoResponse));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void actualizarSolicitud(int idOrdenCompra, String codigoTienda, String estado, String observaciones){
        SolicitudResponse solicitudResponse = new SolicitudResponse(estado,observaciones,idOrdenCompra);
        String topic = ("_"+codigoTienda+"_solicitudes");
        try {
            kafkaTemplate.send(topic,objectMapper.writeValueAsString(solicitudResponse));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = "_orden-de-compra", groupId = "default")
    public void recibirOrden(String message){
        try {
            OrdenDTO ordenDTO = objectMapper.readValue(message,OrdenDTO.class);
            this.procesarOrden(ordenDTO.getItems(),ordenDTO.getCodigoTienda(),ordenDTO.getIdOrdenDeCompra(),ordenDTO.getFechaSolicitud());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

