package com.soap.server.endpoints;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soap.server.service.CatalogoService;

import lombok.RequiredArgsConstructor;
import stockeate.AgregarCatalogoRequest;
import stockeate.AgregarCatalogoResponse;
import stockeate.EliminarCatalogoRequest;
import stockeate.EliminarCatalogoResponse;
import stockeate.ModificarCatalogoRequest;
import stockeate.ModificarCatalogoResponse;
import stockeate.PdfCatalogoRequest;
import stockeate.PdfCatalogoResponse;
import stockeate.TraerCatalogosRequest;
import stockeate.TraerCatalogosResponse;



@Endpoint
@RequiredArgsConstructor
public class CatalogoEndPoint {

    private static final String NAMESPACE_URL = "http://stockeate";
    private final CatalogoService catalogoService;

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "agregarCatalogoRequest")
    @ResponsePayload
    public AgregarCatalogoResponse agregarCatalogo(@RequestPayload AgregarCatalogoRequest request){
        return catalogoService.agregarCatalogo(request.getNombre(),request.getCodigoTienda(),request.getIds());
    }
/*
    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "modificarCatalogoRequest")
    @ResponsePayload
    public ModificarCatalogoResponse modificarCatalogo(@RequestPayload ModificarCatalogoRequest request){
        return catalogoService.modificarCatalogo(request.getId(), request.getIdProducto());
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "eliminarCatalogosRequest")
    @ResponsePayload
    public EliminarCatalogoResponse eliminarCatalogos(@RequestPayload EliminarCatalogoRequest request){
        return catalogoService.eliminarCatalogos(request.getCodigoTienda());
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "traerCatalogosRequest")
    @ResponsePayload
    public TraerCatalogosResponse traerCatalogos(@RequestPayload TraerCatalogosRequest request){
        return catalogoService.traerCatalogos(request.getCodigoTienda());
    }
*/
    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "pdfCatalogoRequest")
    @ResponsePayload
    public PdfCatalogoResponse pdfCatalogo(@RequestPayload PdfCatalogoRequest request){
        return catalogoService.pdfCatalogo(request);
    }

}
