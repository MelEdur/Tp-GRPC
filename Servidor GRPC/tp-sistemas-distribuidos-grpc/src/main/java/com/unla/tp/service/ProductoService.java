package com.unla.tp.service;

import com.unla.tp.entity.*;
import com.unla.grpc.*;
import com.unla.tp.repository.*;
import com.unla.tp.util.SecurityUtils;
import io.grpc.stub.StreamObserver;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@GRpcService
public class ProductoService extends ProductoServiceGrpc.ProductoServiceImplBase {

    private final IProductoRepository productoRepository;
    private final IStockRepository stockRepository;
    private final ITiendaRepository tiendaRepository;
    @Override
    public void agregarProducto(AgregarProductoRequest request, StreamObserver<Id> responseObserver) {
        List<ProductoEntity> productoEntityList = productoRepository.findAll();
        String codigoProducto;
        codigoProducto = generarStringAleatorio();
        int contador = productoEntityList.size();
        while (contador > 0) {
            contador = productoEntityList.size();
            for (int i = 0; i < productoEntityList.size(); i++) {
                if (productoEntityList.get(i).getCodigoProducto() != codigoProducto) {
                    contador--;
                }
            }
        }
        // Guarda id del nuevo objeto agregado
        Id id = Id.newBuilder()

                // Agrega el objeto tomando los datos de la request
                .setId(productoRepository.save(ProductoEntity.builder()
                        .codigoProducto(codigoProducto)
                        .nombreProducto(request.getProducto().getNombreProducto())
                        .talle(request.getProducto().getTalle())
                        .color(request.getProducto().getColor())
                        .foto(request.getProducto().getFoto())
                        .habilitado(true)
                        .build())
                        .getId())
                .build();
        int idProducto= id.getId();
        // -------------
        List<Integer> ids = new ArrayList<>();
        
        ids = request.getIdList().stream().map(idd -> idd.getId()).toList();
        //request.getIdList().stream().map(idTienda ->  {
            for (int i=0; i<=ids.size(); i++) {
            TiendaEntity tienda = tiendaRepository.findById(ids.get(i))
                .orElseThrow(() -> new EntityNotFoundException("No existe un producto con esa id"));
            ProductoEntity producto = productoRepository.findById(idProducto)
                .orElseThrow(() -> new EntityNotFoundException("No existe un producto con esa id"));

                //List<TiendaEntity> tiendaEntityAux = tiendaRepository.findByIdtiendaContaining(ids.get(i));
                List<StockEntity> listStock = stockRepository.findAll();
                int lastId = listStock.size();
                StockEntity stock = new StockEntity();
                stock.setIdStock(lastId+i+1);
                stock.setProducto(producto);
                stock.setTienda(tienda);
                stock.setCantidad(0);
                stock.setHabilitado(true);
                Id id2 = Id.newBuilder()
                        .setId(stockRepository.save(stock).getIdStock())
                        .build();}
        // -------------

        // Devuelve la id
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    public static String generarStringAleatorio() {
        // Definir los caracteres posibles
        int longitud = 10;
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder stringAleatorio = new StringBuilder(longitud);

        // Generar la cadena
        for (int i = 0; i < longitud; i++) {
            int indice = random.nextInt(caracteres.length());
            stringAleatorio.append(caracteres.charAt(indice));
        }

        return stringAleatorio.toString();
    }

    @Override
    public void traerProductos(Empty request, StreamObserver<ProductosLista> responseObserver) {
        // Busca los Objetos en la DB
        List<ProductoEntity> productoEntityList = productoRepository.findAll();

        // Crea objeto que puede ser enviado por grpc
        ProductosLista productosLista = ProductosLista.newBuilder()
                .addAllProductos(productoEntityList.stream()
                        // Recorre los objetos traidos y los convierte en una lista que puede enviarse
                        .map(productoEntity -> Producto.newBuilder()
                                .setId(productoEntity.getId())
                                .setCodigoProducto(productoEntity.getCodigoProducto())
                                .setNombreProducto(productoEntity.getNombreProducto())
                                .setTalle(productoEntity.getTalle())
                                .setColor(productoEntity.getColor())
                                .setFoto(productoEntity.getFoto())
                                .setHabilitado(productoEntity.isHabilitado())
                                .build())
                        // convertir a lista
                        .collect(Collectors.toList()))
                .build();
        // Devuelve la lista de objetos
        responseObserver.onNext(productosLista);
        responseObserver.onCompleted();
    }

    @Override
    public void eliminarProducto(Id request, StreamObserver<Id> responseObserver) {
        // Busca Objeto en la DB
        ProductoEntity producto = productoRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("No existe un producto con esa id"));
        List<StockEntity> stockEntityList = stockRepository.findByProducto(producto);
        
        for (StockEntity stockEntity : stockEntityList) {
                stockEntity.setHabilitado(false);
            Id id = Id.newBuilder()
                .setId(stockRepository.save(stockEntity).getIdStock())
                        .build();
        }
        // Crea Objeto que puede ser enviado por grpc
        Id id = Id.newBuilder()
                .setId(productoRepository.save(ProductoEntity.builder()
                        .id(request.getId())
                        .codigoProducto(producto.getCodigoProducto())
                        .nombreProducto(producto.getNombreProducto())
                        .talle(producto.getTalle())
                        .color(producto.getColor())
                        .foto(producto.getFoto())
                        .habilitado(false)
                        .build())
                        .getId())
                .build();

        // Envía el objeto
        responseObserver.onNext(Id.newBuilder()
                .setId(request.getId())
                .build());
        responseObserver.onCompleted();
    }

    // Productos: se pueden filtrar por nombre, código, talle, color.
    @Override
    public void traerProductosPorFiltro(FiltroProducto request, StreamObserver<ProductosLista> responseObserver) {
        // Busca los Objetos en la DB
        List<ProductoEntity> productoEntityList = productoRepository.findByCodigoProductoContainingAndNombreProductoContainingAndTalleContainingAndColorContaining(request.getCodigoProducto(), request.getNombreProducto(), request.getTalle(), request.getColor());
        
        // Crea objeto que puede ser enviado por grpc
        ProductosLista productosLista = ProductosLista.newBuilder()
                .addAllProductos(productoEntityList.stream()
                        // Recorre los objetos traidos y los convierte en una lista que puede enviarse
                        .map(productoEntity -> Producto.newBuilder()
                                .setId(productoEntity.getId())
                                .setCodigoProducto(productoEntity.getCodigoProducto())
                                .setNombreProducto(productoEntity.getNombreProducto())
                                .setTalle(productoEntity.getTalle())
                                .setColor(productoEntity.getColor())
                                .setFoto(productoEntity.getFoto())
                                .setHabilitado(productoEntity.isHabilitado())
                                .build())
                        // convertir a lista
                        .collect(Collectors.toList()))
                .build();
        // Devuelve la lista de objetos
        responseObserver.onNext(productosLista);
        responseObserver.onCompleted();
    }

    @Override
    public void modificarProducto(Producto request, StreamObserver<Id> responseObserver) {

        // Busca Objeto en la DB
        ProductoEntity producto = productoRepository.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException("No existe un producto con esa id"));

        // Crea Objeto que puede ser enviado por grpc
        Id id = Id.newBuilder()
                .setId(productoRepository.save(ProductoEntity.builder()
                        .id(producto.getId())
                        .codigoProducto(producto.getCodigoProducto())
                        .nombreProducto(request.getNombreProducto())
                        .talle(request.getTalle())
                        .color(request.getColor())
                        .foto(request.getFoto())
                        .habilitado(request.getHabilitado())
                        .build())
                        .getId())
                .build();

        // Envía el objeto
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }
}