package com.unla.tp.service;

import com.unla.grpc.AuthServiceGrpc;
import com.unla.grpc.LoginRequest;
import com.unla.grpc.LoginResponse;
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
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {

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
        if("USUARIO".equals(usuario.get().getRol())){
        LoginResponse loginResponse = LoginResponse.newBuilder()
                .setJwt(securityUtils.generarToken(usuario.get().getRol(),nombreUsuario))
                .setUsuario(usuario.get().getNombreUsuario())
                .setRol(usuario.get().getRol())
                .setCodigoTienda(usuario.get().getCodigoTienda())
                .setIdUsuario(usuario.get().getId())
                .build();

        responseObserver.onNext(loginResponse);
        responseObserver.onCompleted();}
        else{LoginResponse loginResponse = LoginResponse.newBuilder()
            .setJwt(securityUtils.generarToken(usuario.get().getRol(),nombreUsuario))
            .setUsuario(usuario.get().getNombreUsuario())
            .setRol(usuario.get().getRol())
            .build(); 
            
        responseObserver.onNext(loginResponse);
        responseObserver.onCompleted();
        }
        
    }
}
