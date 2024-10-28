package com.soap.server.endpoints;


import com.soap.server.service.FiltroService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import stockeate.TraerFiltrosRequest;
import stockeate.TraerFiltrosResponse;


import stockeate.GuardarFiltroResponse;
import stockeate.GuardarFiltroRequest;

import stockeate.EditarFiltroResponse;
import stockeate.EditarFiltroRequest;

import stockeate.EliminarFiltroResponse;
import stockeate.EliminarFiltroRequest;


@Endpoint
@RequiredArgsConstructor
public class FiltroEndpoint {

    private static final String NAMESPACE_URI = "http://stockeate";
    private final FiltroService filtroService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "traerFiltrosRequest")
    @ResponsePayload
    public TraerFiltrosResponse traerFiltros(@RequestPayload TraerFiltrosRequest request){
        return filtroService.traerFiltros(request.getIdUsuario());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "guardarFiltroRequest")
    @ResponsePayload
    public GuardarFiltroResponse guardarFiltro(@RequestPayload GuardarFiltroRequest request){
        return filtroService.guardarFiltro(request.getCodigoProducto(), request.getFechaDesde(), request.getFechaHasta(), request.isEstado(), request.getCodigoTienda(), request.getIdUsuario());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "editarFiltroRequest")
    @ResponsePayload
    public EditarFiltroResponse editarFiltro(@RequestPayload EditarFiltroRequest request){
        return filtroService.editarFiltro(request.getCodigoProducto(), request.getFechaDesde(), request.getFechaHasta(), request.isEstado(), request.getCodigoTienda(), request.getIdFiltro());
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "eliminarFiltroRequest")
    @ResponsePayload
    public EliminarFiltroResponse eliminarFiltro(@RequestPayload EliminarFiltroRequest request){
        return filtroService.eliminarFiltro(request.getIdFiltro());
    }

}
