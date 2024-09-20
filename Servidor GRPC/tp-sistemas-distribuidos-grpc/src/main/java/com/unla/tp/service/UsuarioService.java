package com.unla.tp.service;

import com.unla.grpc.*;
import com.unla.tp.entity.UsuarioEntity;
import com.unla.tp.repository.IUsuarioRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@GRpcService
public class UsuarioService extends UsuarioServiceGrpc.UsuarioServiceImplBase {

    private final IUsuarioRepository usuarioRepository;

    @Override
    public void agregarUsuario(CrearUsuarioRequest request, StreamObserver<Id> responseObserver) {


        if(usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).isPresent()){
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription("El nombre de usuario ingresado no se encuentra disponible").asRuntimeException());
            return;
        }

        Id id = Id.newBuilder()
                .setId(usuarioRepository.save(UsuarioEntity.builder()
                        .nombreUsuario(request.getNombreUsuario())
                        .contrasenia(request.getContrasenia())
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

        Optional<UsuarioEntity> usuarioModificar = usuarioRepository.findById(request.getId());
        if (usuarioModificar.isEmpty()) {
            responseObserver.onError((Status.NOT_FOUND.withDescription("El usuario no existe").asRuntimeException()));
            return;
        }

        if(usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).isPresent()){
            responseObserver.onError(Status.ALREADY_EXISTS.withDescription("El nombre de usuario ingresado no se encuentra disponible").asRuntimeException());
            return;
        }

        UsuarioEntity usuario = usuarioModificar.get();

        usuario.setNombreUsuario(request.getNombreUsuario());
        usuario.setNombre(request.getNombre());
        usuario.setContrasenia(request.getContrasenia());
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


        List<UsuarioEntity> usuarios= usuarioRepository
                .findByNombreContainingAndCodigoTiendaContaining(request.getNombre(),request.getCodigoTienda());


        UsuariosLista usuariosLista = UsuariosLista.newBuilder()
                .addAllUsuarios(usuarios.stream()
                        .map(usuarioEntity -> Usuario.newBuilder()
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
