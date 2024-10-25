package com.unla.tp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.unla.tp.entity.Item;
import com.unla.tp.entity.OrdenDeCompra;
import com.unla.tp.entity.OrdenRequest;
import com.unla.tp.entity.ProductoEntity;
import com.unla.tp.entity.StockEntity;
import com.unla.tp.entity.TiendaEntity;
import com.unla.tp.repository.IOrdenDeCompraRepository;
import com.unla.tp.repository.IProductoRepository;
import com.unla.tp.repository.IStockRepository;
import com.unla.tp.repository.ITiendaRepository;
import com.unla.tp.service.RecepcionService;
import com.unla.tp.service.SolicitudesService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin()
public class OrdenController {
    private final IOrdenDeCompraRepository _ordenDeCompraRepository;
    private final IStockRepository _stockRepository;
    private final ITiendaRepository _tiendaRepository;
    private final IProductoRepository _productoRepository;
    private final SolicitudesService solicitudesService;
    private final RecepcionService recepcionService;

    @GetMapping("/ordenesAceptadas")
    public List<OrdenDeCompra> enviarOdenesAceptadasyDespacho(){

        List<OrdenDeCompra> listaOrdenesAceptadas = new ArrayList<>();
        listaOrdenesAceptadas = _ordenDeCompraRepository.findByEstadoAndOrdenDeDespachoIsNotNull("aceptado");

        return listaOrdenesAceptadas;
    }

    @PostMapping("/procesarOrdenesRecibidas")
    public void procesarOrdenesRecibidasyEnviarAlTopic(@RequestBody List<OrdenDeCompra> ordenesRecibidas){

        for (OrdenDeCompra ordenDeCompra : ordenesRecibidas) {
            OrdenDeCompra ordenDeCompraModificable = _ordenDeCompraRepository.findById(ordenDeCompra.getIdOrdenDeCompra())
                .orElseThrow(()-> new RuntimeException("No existe orden de compra con tal id"));
            ordenDeCompraModificable.setEstado("recibido");
            ordenDeCompraModificable.setFechaDeRecepcion(LocalDate.now());

            _ordenDeCompraRepository.save(ordenDeCompraModificable);

            TiendaEntity tienda = _tiendaRepository.findByCodigoTienda(ordenDeCompraModificable.getCodigoTienda()).get();
            
            for (Item item : ordenDeCompraModificable.getItems()) {
                String codigoProducto = item.getCodigo();
                ProductoEntity producto = _productoRepository.findByCodigoProducto(codigoProducto).get(0);

                StockEntity stockModificable = _stockRepository.findByProductoIdAndTiendaId(producto.getId(), tienda.getId()).get().get(0);
                stockModificable.setCantidad(stockModificable.getCantidad() + item.getCantidad());
                _stockRepository.save(stockModificable);
            }
        
            solicitudesService.enviarRecepcion(ordenDeCompraModificable.getOrdenDeDespacho().getIdOrdenDeDespacho(), 
                ordenDeCompraModificable.getFechaDeRecepcion());

        }

        
    }

    @PostMapping("/generarOrdenDeCompra")
    public void generarOrdenDeCompraYManejarTopics(@RequestBody OrdenRequest ordenRequest){
        recepcionService.enviarOrden(ordenRequest.getCodigo(), ordenRequest.getItems());
    }

}
