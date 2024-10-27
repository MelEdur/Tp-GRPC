package com.soap.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soap.server.entity.OrdenDeCompra;

@Repository
public interface IOrdenDeCompraRepository extends JpaRepository<OrdenDeCompra,Integer> {
}
