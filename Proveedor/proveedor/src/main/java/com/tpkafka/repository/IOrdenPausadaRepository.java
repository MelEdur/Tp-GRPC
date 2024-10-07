package com.tpkafka.repository;

import com.tpkafka.entity.OrdenPausada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrdenPausadaRepository extends JpaRepository<OrdenPausada,Integer> {
}
