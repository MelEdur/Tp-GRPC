package com.unla.tp.service;

import com.unla.grpc.AuthServiceGrpc;
import com.unla.grpc.LoginRequest;
import com.unla.grpc.Token;
import com.unla.tp.entity.UsuarioEntity;
import com.unla.tp.repository.IUsuarioRepository;
import com.unla.tp.util.SecurityUtils;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcService;

import java.util.Optional;

@RequiredArgsConstructor
@GRpcService
public class AuthService extends AuthServiceGrpc.AuthServiceImplBase {

    private final IUsuarioRepository usuarioRepository;
    private final SecurityUtils securityUtils;

    @Override
    public void login(LoginRequest request, StreamObserver<Token> responseObserver) {

        String nombreUsuario = request.getUsuario();
        String contrasenia = request.getContrasenia();

        Optional<UsuarioEntity> usuario = usuarioRepository.findByNombreUsuario(nombreUsuario);

        //Validamos que el usuario exista
        if(usuario.isEmpty()){
            responseObserver.onError(Status.NOT_FOUND.withDescription("No existe un usuario con el nombre: "+nombreUsuario).asRuntimeException());
            return;
        }
        //Validamos que la contrasenia sea correcta
        if(!securityUtils.getPasswordEncoder().matches(contrasenia,usuario.get().getContrasenia())){
            responseObserver.onError(Status.INVALID_ARGUMENT.withDescription("Contrasenia incorrecta").asRuntimeException());
            return;
        }

        Token token = Token.newBuilder()
                .setJwt(securityUtils.generarToken(usuario.get().getRol(),nombreUsuario))
                .build();

        responseObserver.onNext(token);
        responseObserver.onCompleted();
    }
}