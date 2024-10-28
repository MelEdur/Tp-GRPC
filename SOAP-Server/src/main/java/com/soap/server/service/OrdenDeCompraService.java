package com.soap.server.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soap.server.entity.ProductoEntity;
import com.soap.server.repository.IOrdenDeCompraRepository;
import com.soap.server.repository.IProductoRepository;

import jakarta.transaction.Transactional;
import stockeate.GetInformeDeCompraResponse;
import stockeate.InformeDeCompra;
import stockeate.ProductoInforme;

@Service
public class OrdenDeCompraService {
    @Autowired
    private IOrdenDeCompraRepository ordenDeCompraRepository;

    @Autowired
    private IProductoRepository productoRepository;

    @Transactional
    public GetInformeDeCompraResponse GetInformesDeCompraPorFiltro(
        String codigoTienda, String estado, LocalDate fechaDesde, LocalDate fechaHasta, String codigoProducto){

        GetInformeDeCompraResponse response = new GetInformeDeCompraResponse();
        //tengo q merter los filtros, filtro de rango de fechas solo por fechadesolicitud
        List<com.soap.server.entity.OrdenDeCompra> ordenesDeCompraDB = ordenDeCompraRepository.
            findByFilters(codigoTienda, estado, fechaDesde, fechaHasta, codigoProducto);

        for (com.soap.server.entity.OrdenDeCompra ordenDeCompraDB : ordenesDeCompraDB) {
            InformeDeCompra auxInformeDeCompra = new InformeDeCompra();

            for (com.soap.server.entity.Item itemCompra : ordenDeCompraDB.getItems()) {

                ProductoEntity productoAux = productoRepository.findByCodigoProducto(itemCompra.getCodigo()).get(0);
                ProductoInforme auxProductoInforme = new ProductoInforme();
                auxProductoInforme.setCodigoProducto(itemCompra.getCodigo());
                auxProductoInforme.setNombre(productoAux.getNombreProducto());
                auxProductoInforme.setCantidadPedida(itemCompra.getCantidad());
                
                auxProductoInforme.setCantidadPedidaTotal(GetPedidosTotalesPorProductoEnOrdenesDeCompra(itemCompra.getIdItem()));

                auxInformeDeCompra.getItems().add(auxProductoInforme);
            }

            auxInformeDeCompra.setIdOrdenDeCompra(ordenDeCompraDB.getIdOrdenDeCompra());   
            if(ordenDeCompraDB.getCodigoTienda() != null){
                auxInformeDeCompra.setCodigoTienda(ordenDeCompraDB.getCodigoTienda());
            }else{
                auxInformeDeCompra.setCodigoTienda("");
            }
            auxInformeDeCompra.setEstado(ordenDeCompraDB.getEstado());

            if(ordenDeCompraDB.getFechaDeRecepcion() != null){
                auxInformeDeCompra.setFechaDeRecepcion(ordenDeCompraDB.getFechaDeRecepcion().toString());
            }

            if(ordenDeCompraDB.getFechaDeSolicitud() != null){
                auxInformeDeCompra.setFechaDeSolicitud(ordenDeCompraDB.getFechaDeSolicitud().toString());
            }

            response.getInformeDeCompraResponse().add(auxInformeDeCompra);
        }
        
        return response;
    };
    
    @Transactional
    private int GetPedidosTotalesPorProductoEnOrdenesDeCompra(int itemId){
        return ordenDeCompraRepository.findTotalCantidadByItemId(itemId); 
    }
}
