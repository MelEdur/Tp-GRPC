package  com.soap.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soap.server.entity.ProductoEntity;

@Repository
public interface IProductoRepository extends JpaRepository<ProductoEntity, Integer>{
    List<ProductoEntity> findByCodigoProductoContainingAndNombreProductoContainingAndTalleContainingAndColorContaining(String codigoProducto, String nombreProducto, String talle, String color);
    boolean existsByCodigoProducto(String codigoProducto);
    List<ProductoEntity> findByCodigoProducto(String codigoProducto);
}
