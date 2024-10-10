package com.unla.tp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.tp.entity.OrdenDeCompra;

@Repository
public interface IOrdenDeCompraRepository extends JpaRepository<OrdenDeCompra,Integer> {
        List<OrdenDeCompra> findByEstadoAndOrdenDeDespachoIsNotNull(String estado);
}
