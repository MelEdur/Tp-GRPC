package com.unla.tp.repository;

import com.unla.tp.entity.OrdenDeCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenDeCompraRepository extends JpaRepository<OrdenDeCompra,Integer> {
}
