package com.soap.server.endpoints;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soap.server.service.OrdenDeCompraService;

import stockeate.GetOrdenDeCompraRequest;
import stockeate.GetOrdenDeCompraResponse;
import stockeate.Item;
import stockeate.OrdenDeCompra;

@Endpoint
public class OrdenDeCompraEndpoint {
    private static final String NAMESPACE_URI = "http://stockeate";

    @Autowired
    private OrdenDeCompraService ordenDeCompraService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdenDeCompraRequest")
    @ResponsePayload
    public GetOrdenDeCompraResponse InformeOrdenDeCompra(@RequestPayload GetOrdenDeCompraRequest request){
        GetOrdenDeCompraResponse response = new GetOrdenDeCompraResponse();
        List<com.soap.server.entity.OrdenDeCompra> ordenesDeCompraResult = ordenDeCompraService.GetOrdenesDeCompraPorFiltro();
        for (com.soap.server.entity.OrdenDeCompra ordenDeCompra : ordenesDeCompraResult) {
            List<Item> listaItemsXml = new ArrayList<Item>();
            System.out.println("entro");

            /* for (com.soap.server.entity.Item itemCompra : ordenDeCompra.getItems()) {
                Item itemResponse = new Item();

                itemResponse.setCantidad(itemCompra.getCantidad());
                itemResponse.setCodigo(itemCompra.getCodigo());
                itemResponse.setIdItem(itemCompra.getIdItem());
                itemResponse.setTalle(itemCompra.getTalle());
                itemResponse.setColor(itemCompra.getColor());
                listaItemsXml.add(itemResponse);

            } */

            OrdenDeCompra ordenResponse = new OrdenDeCompra();

            ordenResponse.setIdOrdenDeCompra(ordenDeCompra.getIdOrdenDeCompra());   

            ordenResponse.setCodigoTienda(ordenDeCompra.getCodigoTienda());
            ordenResponse.setEstado(ordenDeCompra.getEstado());

/*             ordenResponse.setFechaDeRecepcion(ordenDeCompra.getFechaDeRecepcion().toString());
 */            ordenResponse.setFechaDeSolicitud(ordenDeCompra.getFechaDeSolicitud().toString());
            ordenResponse.setObservaciones(ordenDeCompra.getObservaciones());

            response.getOrdenDeCompraResponse().add(ordenResponse);
        }
        return response;
    }
}
