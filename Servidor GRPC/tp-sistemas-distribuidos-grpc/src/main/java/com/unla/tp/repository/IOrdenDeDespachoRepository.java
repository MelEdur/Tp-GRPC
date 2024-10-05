package com.unla.tp.repository;

import com.unla.tp.entity.OrdenDeDespacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenDeDespachoRepository extends JpaRepository<OrdenDeDespacho,Integer> {
}
