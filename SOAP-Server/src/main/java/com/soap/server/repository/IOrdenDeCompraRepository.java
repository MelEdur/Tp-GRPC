package com.soap.server.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.soap.server.entity.OrdenDeCompra;

@Repository
public interface IOrdenDeCompraRepository extends JpaRepository<OrdenDeCompra,Integer> {
    
     @Query("SELECT o FROM OrdenDeCompra o JOIN o.items i " +
           "WHERE (:codigoTienda IS NULL OR o.codigoTienda = :codigoTienda) " +
           "AND (:estado IS NULL OR o.estado = :estado) " +
           "AND (:fechaDeSolicitud IS NULL OR o.fechaDeSolicitud = :fechaDeSolicitud) " +
           "AND (:codigoItem IS NULL OR i.codigo = :codigoItem)")
    List<OrdenDeCompra> findByFilters(
            @Param("codigoTienda") String codigoTienda,
            @Param("estado") String estado,
            @Param("fechaDeSolicitud") LocalDate fechaDeSolicitud,
            @Param("codigoItem") String codigoItem);

    @Query("SELECT o FROM OrdenDeCompra o JOIN o.items i WHERE i.idItem  = :idItem")
    List<OrdenDeCompra> findByItemId(@Param("idItem") int idItem);

    @Query("SELECT SUM(i.cantidad) FROM OrdenDeCompra o JOIN o.items i WHERE i.idItem = :itemId")
    int findTotalCantidadByItemId(@Param("itemId") int itemId);
}
