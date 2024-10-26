package com.unla.tp.repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.unla.tp.entity.OrdenDeCompra;

@Repository
public interface IOrdenDeCompraRepository extends JpaRepository<OrdenDeCompra,Integer> {
        List<OrdenDeCompra> findByEstadoAndOrdenDeDespachoIsNotNull(String estado);

        @Lock(LockModeType.PESSIMISTIC_WRITE)
        @Query("SELECT o FROM OrdenDeCompra o WHERE o.idOrdenDeCompra = :id")
        Optional<OrdenDeCompra> findByIdWithLock(int id);
}
