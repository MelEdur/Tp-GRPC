package com.unla.tp.repository;

import com.unla.tp.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {

    Optional<UsuarioEntity> findByNombreUsuario(String nombreUsuario);
}
