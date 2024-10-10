package com.unla.tp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.unla.tp.entity.StockProveedor;

@Repository
public interface IStockProveedorRepository extends JpaRepository<StockProveedor,Integer> {
    StockProveedor findByCodigo(String codigo);
}
