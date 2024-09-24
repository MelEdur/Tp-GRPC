package com.unla.tp.service;

import com.unla.grpc.*;
import com.unla.tp.entity.UsuarioEntity;
import com.unla.tp.repository.IUsuarioRepository;
import com.unla.tp.util.ContextKeys;
import com.unla.tp.util.SecurityUtils;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@GRpcService
public class UsuarioService extends UsuarioServiceGrpc.UsuarioServiceImplBase {

    private final IUsuarioRepository usuarioRepository;
    private final SecurityUtils securityUtils;

    @Override
    public void agregarUsuario(CrearUsuarioRequest request, StreamObserver<Id> responseObserver) {
        if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;

        if(request.getNombreUsuario().isEmpty() || request.getNombre().isEmpty()
                || request.getApellido().isEmpty() || request.getContrasenia().isEmpty() || request.getCodigoTienda().isEmpty()){
            responseObserver.onError((Status.INVALID_ARGUMENT.withDescription("Los campos no deben estar vacios").asRuntimeException()));
        }

        if(usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())){
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription("El nombre de usuario ingresado no se encuentra disponible").asRuntimeException());
            return;
        }

        Id id = Id.newBuilder()
                .setId(usuarioRepository.save(UsuarioEntity.builder()
                        .nombreUsuario(request.getNombreUsuario())
                        .contrasenia(securityUtils.getPasswordEncoder().encode(request.getContrasenia()))
                        .nombre(request.getNombre())
                        .apellido(request.getApellido())
                        .habilitado(request.getHabilitado())
                        .codigoTienda(request.getCodigoTienda())
                        .rol("USUARIO")
                        .build()).getId())
                .build();
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    @Override
    public void modificarUsuario(Usuario request, StreamObserver<Id> responseObserver) {
        if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;

        if(request.getNombreUsuario().isEmpty() || request.getNombre().isEmpty()
                || request.getApellido().isEmpty() || request.getCodigoTienda().isEmpty()){
            responseObserver.onError((Status.INVALID_ARGUMENT.withDescription("Complete los campos requeridos").asRuntimeException()));
        }


        Optional<UsuarioEntity> usuarioModificar = usuarioRepository.findById(request.getId());
        if (usuarioModificar.isEmpty()) {
            responseObserver.onError((Status.NOT_FOUND.withDescription("El usuario no existe").asRuntimeException()));
            return;
        }
        UsuarioEntity usuario = usuarioModificar.get();

        if(!usuario.getNombreUsuario().equals(request.getNombreUsuario()) && usuarioRepository.existsByNombreUsuario(request.getNombreUsuario())){
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription("El nombre de usuario ingresado no se encuentra disponible").asRuntimeException());
            return;
        }

        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setNombre(request.getNombre());
        if(!request.getContrasenia().isEmpty()){
            usuario.setContrasenia(securityUtils.getPasswordEncoder().encode(request.getContrasenia()));
        }
        usuario.setApellido(request.getApellido());
        usuario.setHabilitado(request.getHabilitado());
        usuario.setCodigoTienda(request.getCodigoTienda());

        Id id = Id.newBuilder()
                .setId(usuarioRepository.save(usuario).getId())
                .build();

        responseObserver.onNext(id);
        responseObserver.onCompleted();

    }

    @Override
    public void traerUsuarios(Empty request, StreamObserver<UsuariosLista> responseObserver) {
        if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;
        List<UsuarioEntity> usuarioEntityList = usuarioRepository.findAll();

        UsuariosLista usuariosLista = UsuariosLista.newBuilder()
                .addAllUsuarios(usuarioEntityList.stream()
                        .map(usuarioEntity -> Usuario.newBuilder()
                                .setId(usuarioEntity.getId())
                                .setNombreUsuario(usuarioEntity.getNombreUsuario())
                                .setNombre(usuarioEntity.getNombre())
                                .setApellido(usuarioEntity.getApellido())
                                .setHabilitado(usuarioEntity.isHabilitado())
                                .setCodigoTienda(usuarioEntity.getCodigoTienda())
                                .build())
                        .toList()
                )
                .build();

        responseObserver.onNext(usuariosLista);
        responseObserver.onCompleted();
    }

    @Override
    public void traerUsuario(Id request, StreamObserver<Usuario> responseObserver) {
        if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;


        Optional<UsuarioEntity> usuarioDb = usuarioRepository.findById(request.getId());
        if (usuarioDb.isEmpty()) {
            responseObserver.onError((Status.NOT_FOUND.withDescription("El usuario no existe").asRuntimeException()));
            return;
        }
        UsuarioEntity usuarioEntity = usuarioDb.get();

        Usuario usuario = Usuario.newBuilder()
                .setId(usuarioEntity.getId())
                .setNombre(usuarioEntity.getNombre())
                .setApellido(usuarioEntity.getApellido())
                .setNombreUsuario(usuarioEntity.getNombreUsuario())
                .setHabilitado(usuarioEntity.isHabilitado())
                .setCodigoTienda(usuarioEntity.getCodigoTienda())
                .build();

        responseObserver.onNext(usuario);
        responseObserver.onCompleted();
    }

    @Override
    public void traerUsuariosPorFiltro(FiltroUsuario request, StreamObserver<UsuariosLista> responseObserver) {
        if (!SecurityUtils.permisos(Set.of("ADMIN"), responseObserver)) return;

        List<UsuarioEntity> usuarios= usuarioRepository
                .findByNombreContainingAndCodigoTiendaContainingOrderByHabilitadoDesc(request.getNombre(),request.getCodigoTienda());


        UsuariosLista usuariosLista = UsuariosLista.newBuilder()
                .addAllUsuarios(usuarios.stream()
                        .map(usuarioEntity -> Usuario.newBuilder()
                                .setId(usuarioEntity.getId())
                                .setNombre(usuarioEntity.getNombre())
                                .setApellido(usuarioEntity.getApellido())
                                .setNombreUsuario(usuarioEntity.getNombreUsuario())
                                .setHabilitado(usuarioEntity.isHabilitado())
                                .setCodigoTienda(usuarioEntity.getCodigoTienda())
                                .build())
                        .toList()
                )
                .build();

        responseObserver.onNext(usuariosLista);
        responseObserver.onCompleted();
    }

}
