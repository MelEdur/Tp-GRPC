package com.unla.tp.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.lognet.springboot.grpc.GRpcService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.unla.grpc.Codigo;
import com.unla.grpc.EliminarProductoDeTiendaRequest;
import com.unla.grpc.Empty;
import com.unla.grpc.Filtro;
import com.unla.grpc.Id;
import com.unla.grpc.ModificarStockRequest;
import com.unla.grpc.StockCompleto;
import com.unla.grpc.StocksLista;
import com.unla.grpc.Tienda;
import com.unla.grpc.TiendaServiceGrpc;
import com.unla.grpc.TiendasLista;
import com.unla.tp.entity.ProductoEntity;
import com.unla.tp.entity.StockEntity;
import com.unla.tp.entity.TiendaEntity;
import com.unla.tp.repository.IProductoRepository;
import com.unla.tp.repository.IStockRepository;
import com.unla.tp.repository.ITiendaRepository;
import com.unla.tp.util.SearchOperation;
import com.unla.tp.util.TiendaSpecificationsBuilder;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

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

        if(spec == null){
                responseObserver.onError((Status.NOT_FOUND.withDescription("Filtro no existe").asRuntimeException()));
        }
        
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
    public void traerStocksPorTienda(Codigo request, StreamObserver<StocksLista> responseObserver) {
        //Busca Objeto en la DB
        Optional<TiendaEntity> tiendaOptional = tiendaRepository.findByCodigoTienda(request.getCodigo());

        if(tiendaOptional.isEmpty()){
            responseObserver.onError((Status.NOT_FOUND.withDescription("La tienda no existe").asRuntimeException()));
        }
        int idTienda = tiendaOptional.get().getId();

        Optional<List<StockEntity>> stocksTienda = stockRepository.findByTiendaId(idTienda);
        
        StocksLista stocksLista = StocksLista.newBuilder()
                .addAllStocksCompleto(stocksTienda.get().stream()
                        .map(stockEntity -> StockCompleto.newBuilder()
                                .setIdStockCompleto(stockEntity.getIdStock())
                                .setCodigoProducto(stockEntity.getProducto().getCodigoProducto())
                                .setNombreProducto(stockEntity.getProducto().getNombreProducto())
                                .setTalle(stockEntity.getProducto().getTalle())
                                .setColor(stockEntity.getProducto().getColor())
                                .setFoto(stockEntity.getProducto().getFoto())
                                .setHabilitado(stockEntity.isHabilitado())
                                .setCodigoTienda(stockEntity.getTienda().getCodigoTienda())
                                .setCantidad(stockEntity.getCantidad())
                                .build()).toList()
                )
                .build();

        //Envía el objeto
        responseObserver.onNext(stocksLista);
        responseObserver.onCompleted();
    }    
}