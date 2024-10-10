package com.tpkafka.repository;

import com.tpkafka.entity.OrdenPausada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrdenPausadaRepository extends JpaRepository<OrdenPausada,Integer> {

    @Query("SELECT o FROM OrdenPausada o JOIN o.items i WHERE i.codigo = :codigo")
    List<OrdenPausada> findByItemCodigo(@Param("codigo") String codigo);

}
