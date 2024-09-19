package com.unla.tp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.unla.tp.entity.TiendaEntity;

@Repository
public interface ITiendaRepository extends JpaRepository<TiendaEntity, Integer>, JpaSpecificationExecutor<TiendaEntity>{
        Optional<TiendaEntity> findByCodigoTienda(String codigoTienda);
}
