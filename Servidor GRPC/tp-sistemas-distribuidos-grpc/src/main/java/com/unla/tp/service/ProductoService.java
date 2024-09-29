package com.unla.tp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.lognet.springboot.grpc.GRpcService;

import com.unla.grpc.AgregarProductoRequest;
import com.unla.grpc.Empty;
import com.unla.grpc.FiltroProducto;
import com.unla.grpc.Id;
import com.unla.grpc.Producto;
import com.unla.grpc.ProductoServiceGrpc;
import com.unla.grpc.ProductosLista;
import com.unla.grpc.StockCompleto;
import com.unla.grpc.StocksLista;
import com.unla.grpc.ProductoStockid;
import com.unla.tp.entity.ProductoEntity;
import com.unla.tp.entity.StockEntity;
import com.unla.tp.entity.TiendaEntity;
import com.unla.tp.repository.IProductoRepository;
import com.unla.tp.repository.IStockRepository;
import com.unla.tp.repository.ITiendaRepository;
import com.unla.tp.util.SecurityUtils;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@GRpcService
public class ProductoService extends ProductoServiceGrpc.ProductoServiceImplBase {

        private final IProductoRepository productoRepository;
        private final IStockRepository stockRepository;
        private final ITiendaRepository tiendaRepository;

        @Override
        public void agregarProducto(AgregarProductoRequest request, StreamObserver<Id> responseObserver) {
                if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;

                String codigoProducto;
                codigoProducto = generarStringAleatorio();
                while (productoRepository.existsByCodigoProducto(codigoProducto)) {
                        codigoProducto = generarStringAleatorio();
                }
                Id id = Id.newBuilder()
                                // Agrega el objeto tomando los datos de la request
                                .setId(productoRepository.save(ProductoEntity.builder()
                                                .codigoProducto(codigoProducto)
                                                .nombreProducto(request.getNombreProducto())
                                                .talle(request.getTalle())
                                                .color(request.getColor())
                                                .foto(request.getFoto())
                                                .habilitado(true)
                                                .build())
                                                .getId())
                                .build();
                int idProducto = id.getId();
                // -------------
                List<Integer> ids = new ArrayList<>();
                ids = request.getIdList().stream().map(idd -> idd.getId()).toList();
                Optional<ProductoEntity> productoAux = productoRepository.findById(idProducto);
                if (productoAux.isEmpty()){
                        responseObserver.onError(Status.NOT_FOUND.withDescription("El producto no existe").asRuntimeException());
                        return;}
                ProductoEntity producto = productoAux.get();
                for (int i = 0; i < ids.size(); i++) {
                        Optional<TiendaEntity> tiendaAux = tiendaRepository.findByCodigoTienda(ids.get(i).toString());
                        if (tiendaAux.isEmpty()){
                                responseObserver.onError(Status.NOT_FOUND.withDescription("La tienda no existe").asRuntimeException());
                                return;}
                        TiendaEntity tienda = tiendaAux.get();
                        List<StockEntity> listStock = stockRepository.findAll();
                        int lastId = listStock.size();
                        StockEntity stock = new StockEntity();
                        stock.setIdStock(lastId + i + 1);
                        stock.setProducto(producto);
                        stock.setTienda(tienda);
                        stock.setCantidad(0);
                        stock.setHabilitado(true);
                        stockRepository.save(stock).getIdStock();
                }
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
                if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;
                // Busca los Objetos en la DB
                List<ProductoEntity> productoEntityList = productoRepository.findAll();

                // Crea objeto que puede ser enviado por grpc
                ProductosLista productosLista = ProductosLista.newBuilder()
                                .addAllProductos(productoEntityList.stream()
                                                // Recorre los objetos traidos y los convierte en una lista que puede
                                                // enviarse
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
                if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;
                // Busca Objeto en la DB
                Optional<ProductoEntity> productoAux = productoRepository.findById(request.getId());
                if (productoAux.isEmpty()){
                        responseObserver.onError(Status.NOT_FOUND.withDescription("El producto no existe").asRuntimeException());
                        return;}
                ProductoEntity producto = productoAux.get();
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
                if (!SecurityUtils.permisos(Set.of("ADMIN","USUARIO"), responseObserver)) return;
                // Busca los Objetos en la DB
                List<ProductoEntity> productoEntityList = productoRepository
                                .findByCodigoProductoContainingAndNombreProductoContainingAndTalleContainingAndColorContaining(
                                                request.getCodigoProducto(), request.getNombreProducto(),
                                                request.getTalle(), request.getColor());

                // Crea objeto que puede ser enviado por grpc

                ProductosLista productosLista = ProductosLista.newBuilder()
                                .addAllProductos(productoEntityList.stream()
                                                // Recorre los objetos traidos y los convierte en una lista que puede
                                                // enviarse
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
        public void modificarProducto(ProductoStockid request, StreamObserver<Id> responseObserver) {
                if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;
                // Busca Objeto en la DB
                Optional<ProductoEntity> productoAux = productoRepository.findById(request.getId());
                if (productoAux.isEmpty()){
                        responseObserver.onError(Status.NOT_FOUND.withDescription("El producto no existe").asRuntimeException());
                        return;}
                        ProductoEntity producto = productoAux.get();
                // Crea Objeto que puede ser enviado por grpc
                Id id = Id.newBuilder()
                                .setId(productoRepository.save(ProductoEntity.builder()
                                                .id(producto.getId())
                                                .codigoProducto(producto.getCodigoProducto())
                                                .nombreProducto(request.getNombreProducto())
                                                .talle(request.getTalle())
                                                .color(request.getColor())
                                                .foto(request.getFoto())
                                                .habilitado(true)
                                                .build())
                                                .getId())
                                .build();

                Optional<StockEntity> stockEntityaux = stockRepository.findById(request.getIdStockCompleto());
                StockEntity stockEntity = stockEntityaux.get();
                stockEntity.setHabilitado(true);
                stockRepository.save(stockEntity);
                // Envía el objeto
                responseObserver.onNext(id);
                responseObserver.onCompleted();
        }

        @Override
        public void traerStocks(Empty request, StreamObserver<StocksLista> responseObserver) {
                if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;
 
                List<StockEntity> stockEntityList = stockRepository.findAll();
       
                        StocksLista stocksLista = StocksLista.newBuilder()
                        .addAllStocksCompleto(stockEntityList.stream()
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
                                        .setIdProducto(stockEntity.getProducto().getId())
                                        .build()).toList()
                        )
                        .build();
                responseObserver.onNext(stocksLista);
                responseObserver.onCompleted();
        }

        @Override
        public void eliminarStock(Id request, StreamObserver<Id> responseObserver) {
                if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;
                // Busca Objeto en la DB
                Optional<StockEntity> stockAux = stockRepository.findById(request.getId());
                if (stockAux.isEmpty()){
                        responseObserver.onError(Status.NOT_FOUND.withDescription("El stock no existe").asRuntimeException());
                        return;}
                StockEntity stock = stockAux.get();
                Id id = Id.newBuilder()
                                .setId(stockRepository.save(StockEntity.builder()
                                                .idStock(stock.getIdStock())
                                                .producto(stock.getProducto())
                                                .tienda(stock.getTienda())
                                                .cantidad(stock.getCantidad())
                                                .habilitado(false)
                                                .build())
                                                .getIdStock())
                                .build();

                // Envía el objeto
                responseObserver.onNext(id);
                responseObserver.onCompleted();
        }

        @Override
        public void traerStocksPorFiltro(FiltroProducto request, StreamObserver<StocksLista> responseObserver) {
                if (!SecurityUtils.permisos(Set.of("ADMIN","USUARIO"), responseObserver)) return;

                List<StockEntity> stockEntityList = stockRepository.findByProductoCodigoProductoContainingAndProductoNombreProductoContainingAndProductoTalleContainingAndProductoColorContainingAndTiendaCodigoTiendaContaining(request.getCodigoProducto(), request.getNombreProducto(),
                request.getTalle(), request.getColor(), request.getCodigoTienda());
                // Crea objeto que puede ser enviado por grpc
       
                        StocksLista stocksLista = StocksLista.newBuilder()
                        .addAllStocksCompleto(stockEntityList.stream()
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
                                        .setIdProducto(stockEntity.getProducto().getId())
                                        .build()).toList()
                        )
                        .build();

                responseObserver.onNext(stocksLista);
                responseObserver.onCompleted();
        }
}

