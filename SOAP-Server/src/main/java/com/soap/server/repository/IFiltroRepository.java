package com.soap.server.repository;

import com.soap.server.entity.Filtro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFiltroRepository extends JpaRepository<Filtro,Integer> {

    
}
