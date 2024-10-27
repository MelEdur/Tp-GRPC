package com.soap.server.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soap.server.entity.CatalogoEntity;
import com.soap.server.entity.TiendaEntity;

@Repository
public interface ICatalogoRepository extends JpaRepository<CatalogoEntity, Integer>{

    List<CatalogoEntity> findByTienda(TiendaEntity tienda);
}
