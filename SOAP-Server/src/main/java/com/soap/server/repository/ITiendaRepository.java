package com.soap.server.repository;

import com.soap.server.entity.TiendaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITiendaRepository extends JpaRepository<TiendaEntity,Integer> {

    public Optional<TiendaEntity> findByCodigoTienda(String codigo);
}
