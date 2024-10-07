package com.unla.tp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.tp.entity.ProductoEntity;
import com.unla.tp.entity.StockEntity;
import com.unla.tp.entity.TiendaEntity;

@Repository
public interface IStockRepository extends JpaRepository<StockEntity, Integer>{
    Optional<List<StockEntity>> findByProductoId(Integer productoId);
    Optional<List<StockEntity>> findByTiendaId(Integer tiendaId);
    Optional<List<StockEntity>> findByProductoIdAndTiendaId(Integer productoId, Integer tiendaId);
    List<StockEntity> findByProducto(ProductoEntity producto);
    List<StockEntity> findByTienda(TiendaEntity tienda);
    List<StockEntity> findByProductoCodigoProductoContainingAndProductoNombreProductoContainingAndProductoTalleContainingAndProductoColorContainingAndTiendaCodigoTiendaContaining(String codigoProducto, String nombreProducto, String talle, String color, String codigoTienda);
}
