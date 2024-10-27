package com.soap.server.service;
import java.util.ArrayList;
import java.util.List;

import org.apache.el.stream.Optional;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.soap.server.entity.CatalogoEntity;
import com.soap.server.entity.ProductoEntity;
import com.soap.server.entity.TiendaEntity;
import com.soap.server.repository.ICatalogoRepository;
import com.soap.server.repository.IProductoRepository;
import com.soap.server.repository.ITiendaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import stockeate.AgregarCatalogoRequest;
import stockeate.AgregarCatalogoResponse;
import stockeate.EliminarCatalogoResponse;
import stockeate.PdfCatalogoRequest;
import stockeate.PdfCatalogoResponse;
import stockeate.ProductoCatalogo;
import stockeate.TraerCatalogosResponse;

@Service
@RequiredArgsConstructor
public class CatalogoService {

    private final ICatalogoRepository catalogoRepository;
    private final IProductoRepository productoRepository;
    private final ITiendaRepository tiendaRepository;

    @Transactional
    public AgregarCatalogoResponse agregarCatalogo( String nombre, String codigoTienda, List<Integer> ids){
        AgregarCatalogoResponse response = new AgregarCatalogoResponse();
        TiendaEntity tienda = tiendaRepository.findByCodigoTienda(codigoTienda).get();
        List<ProductoEntity> productos = new ArrayList<>();
        for(int id: ids){
            productos.add(productoRepository.findById(id).get());
        }
        CatalogoEntity catalogo = catalogoRepository.save(CatalogoEntity.builder()
                                        .nombre(nombre)
                                        .productos(productos)
                                        .tienda(tienda)
                                        .build());

        return response;
    }
/* 
    @Override
    public AgregarProductoCatalogoResponse agregarProductoCatalogo(ProductoCatalogo productoCatalogo){
        AgregarProductoCatalogoResponse response = new AgregarProductoCatalogoResponse();
        Optional<ProductoEntity> productoOpt = productoRepository.findById(productoCatalogo.getIdProducto());
        ProductoEntity producto = productoOpt;
        catalogoRepository.save(CatalogoEntity.builder()
                                .id(productoCatalogo.getId())
                                .producto(producto)
                                .build());
        return response;
    }

    public EliminarProductoCatalogoResponse eliminarProductoCatalogo(){
        EliminarProductoCatalogoResponse response = new EliminarProductoCatalogoResponse();
        
        return response;
    }


    public EliminarCatalogoResponse eliminarCatalogo(int id){
        EliminarCatalogoResponse response = new EliminarCatalogoResponse();
        Optional<CatalogoEntity> catalogoAux = catalogoRepository.findById(id);
        CatalogoEntity catalogo = catalogoAux;
        catalogoRepository.delete(catalogo);
        return response;
    }


    public TraerCatalogosResponse traerCatalogos(String codigoTienda){
        TraerCatalogosResponse response = new TraerCatalogosResponse();
        response = catalogoRepository.findByCodigoTienda(codigoTienda);
        return response;
    }


    public PdfCatalogoResponse pdfCatalogo(@RequestPayload PdfCatalogoRequest request){
        PdfCatalogoResponse response = new PdfCatalogoResponse();
        
        return response;
    }
*/
}
