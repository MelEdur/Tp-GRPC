package com.soap.server.endpoints;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soap.server.service.OrdenDeCompraService;

import stockeate.GetInformeDeCompraRequest;
import stockeate.GetInformeDeCompraResponse;

@Endpoint
public class OrdenDeCompraEndpoint {
    private static final String NAMESPACE_URI = "http://stockeate";

    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInformeDeCompraRequest")
    @ResponsePayload
    public GetInformeDeCompraResponse getInformeDeCompra(@RequestPayload GetInformeDeCompraRequest request){  
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      
        return ordenDeCompraService.GetInformesDeCompraPorFiltro(request.getCodigoTienda(), request.getEstado(),
            LocalDate.parse(request.getFechaDesde(), formatter), LocalDate.parse(request.getFechaHasta(), formatter), request.getCodigoProducto());
    }
}
