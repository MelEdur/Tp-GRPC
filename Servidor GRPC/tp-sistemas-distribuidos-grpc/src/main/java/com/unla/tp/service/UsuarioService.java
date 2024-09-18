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
import java.util.Set;

@RequiredArgsConstructor
@GRpcService
public class UsuarioService extends UsuarioServiceGrpc.UsuarioServiceImplBase {

    private final IUsuarioRepository usuarioRepository;

    @Override
    public void agregarUsuario(CrearUsuarioRequest request, StreamObserver<Id> responseObserver) {

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

        Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(request.getId());

        if (usuarioOptional.isEmpty()) {
            responseObserver.onError((Status.NOT_FOUND.withDescription("El usuario no existe").asRuntimeException()));
            return;
        }
        UsuarioEntity usuario = usuarioOptional.get();

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

        UsuarioEntity usuarioEntity = usuarioRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Usuario not found"));

        Usuario usuario = Usuario.newBuilder()
                .setNombre(usuarioEntity.getNombre())
                .setApellido(usuarioEntity.getApellido())
                .setNombreUsuario(usuarioEntity.getNombreUsuario())
                .setHabilitado(usuarioEntity.isHabilitado())
                .setCodigoTienda(usuarioEntity.getCodigoTienda())
                .build();

        responseObserver.onNext(usuario);
        responseObserver.onCompleted();
    }
}
