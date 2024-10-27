package com.soap.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soap.server.entity.CatalogoEntity;

@Repository
public interface ICatalogoRepository extends JpaRepository<CatalogoEntity, Integer>{


}
