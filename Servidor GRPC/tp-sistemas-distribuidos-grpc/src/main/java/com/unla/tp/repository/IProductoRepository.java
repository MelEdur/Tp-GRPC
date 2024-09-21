package com.unla.tp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.unla.tp.entity.ProductoEntity;

@Repository
public interface IProductoRepository extends JpaRepository<ProductoEntity, Integer>{
    List<ProductoEntity> findByCodigoProductoContainingAndNombreProductoContainingAndTalleContainingAndColorContaining(String codigoProducto, String nombreProducto, String talle, String color);
    boolean existByCodigoProducto(String codigoProducto);
}
