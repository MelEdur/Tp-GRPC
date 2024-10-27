package com.soap.server.repository;

import com.soap.server.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsuarioRepository extends JpaRepository<UsuarioEntity,Integer> {

    public Optional<UsuarioEntity> findByNombreUsuario(String usuario);
}
