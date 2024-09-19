package com.unla.tp.service;

import com.google.common.base.Joiner;
import com.unla.tp.entity.ProductoEntity;
import com.unla.tp.entity.StockEntity;
import com.unla.tp.entity.TiendaEntity;

import com.unla.tp.util.SearchOperation;
import com.unla.tp.util.TiendaSpecification;
import com.unla.tp.util.TiendaSpecificationsBuilder;
import com.unla.tp.util.SpecSearchCriteria;

import com.unla.grpc.*;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.unla.tp.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Optional;

@RequiredArgsConstructor
@GRpcService
public class TiendaService extends TiendaServiceGrpc.TiendaServiceImplBase{
    private final ITiendaRepository tiendaRepository;
    private final IStockRepository  stockRepository;
    private final IProductoRepository  productoRepository;

    @Override
    public void agregarTienda(Tienda request, StreamObserver<Id> responseObserver) {
        //Guarda id del nuevo objeto agregado
        Id id = Id.newBuilder()

                //Agrega el objeto tomando los datos de la request
                .setId(tiendaRepository.save(TiendaEntity.builder()
                        // .id(request.geti())
                        .codigoTienda(request.getCodigoTienda())
                        .direccion(request.getDireccion())
                        .ciudad(request.getCiudad())
                        .provincia(request.getProvincia())
                        .habilitada(request.getHabilitada())
                        .build())
                        .getId())
                .build();

        //Devuelve la id
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    @Override
    public void traerTienda(Id request, StreamObserver<Tienda> responseObserver) {

        //Busca Objeto en la DB
        TiendaEntity tiendaEntity = tiendaRepository.findById(request.getId())
                .orElseThrow(()->new EntityNotFoundException("No existe una Tienda con esa id"));

        //Crea Objeto que puede ser enviado por grpc
        Tienda tienda = Tienda.newBuilder()
                .setCodigoTienda(tiendaEntity.getCodigoTienda())
                .setDireccion(tiendaEntity.getDireccion())
                .setCiudad(tiendaEntity.getCiudad())
                .setProvincia(tiendaEntity.getProvincia())
                .setHabilitada(tiendaEntity.getHabilitada())
                .build();

        //Envía el objeto
        responseObserver.onNext(tienda);
        responseObserver.onCompleted();
    }

    @Override
    public void modificarTienda(Tienda request, StreamObserver<Id> responseObserver) {
        Optional<TiendaEntity> tiendaOptional = tiendaRepository.findByCodigoTienda(request.getCodigoTienda());

        if(tiendaOptional.isEmpty()){
            responseObserver.onError((Status.NOT_FOUND.withDescription("La tienda no existe").asRuntimeException()));
        }else{
            TiendaEntity tienda = tiendaOptional.get();
            tienda.setCodigoTienda(request.getCodigoTienda());
            tienda.setDireccion(request.getDireccion());
            tienda.setCiudad(request.getCiudad());
            tienda.setProvincia(request.getProvincia());
            tienda.setHabilitada(request.getHabilitada());
        
            Id id = Id.newBuilder()
                    .setId(tiendaRepository.save(tienda).getId())
                    .build();

            //Envía el objeto
            responseObserver.onNext(id);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void traerTiendas(Empty request, StreamObserver<TiendasLista> responseObserver) {

        //Busca los Objetos en la DB
        List<TiendaEntity> tiendaEntityList = tiendaRepository.findAll();

        //Crea objeto que puede ser enviado por grpc
        TiendasLista tiendaLista = TiendasLista.newBuilder()
                .addAllTiendas(tiendaEntityList.stream()
                        //Recorre los objetos traidos y los convierte en una lista que puede enviarse
                        .map(tiendaEntity -> Tienda.newBuilder()
                                .setCodigoTienda(tiendaEntity.getCodigoTienda())
                                .setDireccion(tiendaEntity.getDireccion())
                                .setCiudad(tiendaEntity.getCiudad())
                                .setProvincia(tiendaEntity.getProvincia())
                                .setHabilitada(tiendaEntity.getHabilitada())
                                .build())
                        //convertir a lista
                        .collect(Collectors.toList()))
                .build();
        //Devuelve la lista de objetos
        responseObserver.onNext(tiendaLista);
        responseObserver.onCompleted();
    }

    @Override
    public void traerTiendasPorFiltro(Filtro request, StreamObserver<TiendasLista> responseObserver) {
        //FALTA
        //viene un codigoTienda:12345,ciudad:bsas
        String search = request.getFiltro();

        TiendaSpecificationsBuilder builder = new TiendaSpecificationsBuilder();

        String operationSetExper = Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET);
        Pattern pattern = Pattern.compile("(\\w+?)(" + operationSetExper + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(4), matcher.group(3), matcher.group(5));
        }
        
        Specification<TiendaEntity> spec = builder.build();

        //Busca los Objetos en la DB
        List<TiendaEntity> tiendaEntityList = tiendaRepository.findAll(spec);

        //Crea objeto que puede ser enviado por grpc
        TiendasLista tiendaLista = TiendasLista.newBuilder()
                .addAllTiendas(tiendaEntityList.stream()
                        //Recorre los objetos traidos y los convierte en una lista que puede enviarse
                        .map(tiendaEntity -> Tienda.newBuilder()
                                .setCodigoTienda(tiendaEntity.getCodigoTienda())
                                .setDireccion(tiendaEntity.getDireccion())
                                .setCiudad(tiendaEntity.getCiudad())
                                .setProvincia(tiendaEntity.getProvincia())
                                .setHabilitada(tiendaEntity.getHabilitada())
                                .build())
                        //convertir a lista
                        .collect(Collectors.toList()))
                .build();
        //Devuelve la lista de objetos
        responseObserver.onNext(tiendaLista);
        responseObserver.onCompleted();
    }
    
    @Transactional
    @Override
    public void eliminarProductoDeTienda(EliminarProductoDeTiendaRequest request, StreamObserver<Id> responseObserver) {

        Optional<List<StockEntity>> stockLisOptional = stockRepository.findByProductoIdAndTiendaId(request.getIdProducto(), request.getIdTienda());
        StockEntity stock = stockLisOptional.get().get(0);

        Optional<ProductoEntity> productoOptional = productoRepository.findById(request.getIdProducto());
        Optional<TiendaEntity> tiendaOptional = tiendaRepository.findById(request.getIdTienda());
        if(tiendaOptional.isEmpty() || productoOptional.isEmpty()){
                responseObserver.onError((Status.NOT_FOUND.withDescription("La tienda/producto no existe").asRuntimeException()));
        }else{
                stock.setHabilitado(false);
        }

        Id id = Id.newBuilder()
                .setId(stockRepository.save(stock).getIdStock())
                .build();

        //Envía el objeto
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    @Transactional
    @Override
    public void modificarStock(ModificarStockRequest request, StreamObserver<Id> responseObserver) {

        Optional<List<StockEntity>> stockLisOptional = stockRepository.findByProductoIdAndTiendaId(request.getIdProducto(), request.getIdTienda());
        StockEntity stock = stockLisOptional.get().get(0);

        Optional<ProductoEntity> productoOptional = productoRepository.findById(request.getIdProducto());
        Optional<TiendaEntity> tiendaOptional = tiendaRepository.findById(request.getIdTienda());
        if(tiendaOptional.isEmpty() || productoOptional.isEmpty()){
                responseObserver.onError((Status.NOT_FOUND.withDescription("La tienda/producto no existe").asRuntimeException()));
        }else{
                TiendaEntity tiendaNueva = tiendaOptional.get();
                ProductoEntity productoNuevo = productoOptional.get();
                
                stock.setProducto(productoNuevo);
                stock.setCantidad(request.getCantidad());
                stock.setTienda(tiendaNueva);
        }

        Id id = Id.newBuilder()
                .setId(stockRepository.save(stock).getIdStock())
                .build();

        //Envía el objeto
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    //  rpc TraerProductosPorTienda(Id) returns(ProductosLista);
    @Transactional
    @Override
    public void traerProductosPorTienda(Id request, StreamObserver<ProductosLista> responseObserver) {
        //Busca Objeto en la DB
        Optional<List<StockEntity>> stocksTienda = stockRepository.findByTiendaId(request.getId());
        List<ProductoEntity> listaProductosEntity = new ArrayList<ProductoEntity>();

        for (StockEntity stock : stocksTienda.get()) {
                listaProductosEntity.add(stock.getProducto());
        }

        //Crea objeto que puede ser enviado por grpc
        ProductosLista productoLista = ProductosLista.newBuilder()
                .addAllProductos(listaProductosEntity.stream()
                        .map(productoEntity -> Producto.newBuilder()
                                .setCodigoProducto(productoEntity.getCodigoProducto())
                                .setNombreProducto(productoEntity.getNombreProducto())
                                .setTalle(productoEntity.getTalle())
                                .setColor(productoEntity.getColor())
                                .setFoto(productoEntity.getFoto())//FALTA EL HABILITADO
                                .build())
                        //convertir a lista
                        .collect(Collectors.toList()))
                .build();

        //Envía el objeto
        responseObserver.onNext(productoLista);
        responseObserver.onCompleted();
    }    
}