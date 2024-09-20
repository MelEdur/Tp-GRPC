package com.unla.tp.util;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class SecurityUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final PasswordEncoder passwordEncoder;

    public SecurityUtils(){
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public PasswordEncoder getPasswordEncoder(){
        return this.passwordEncoder;
    }

    public static boolean permisos(Set<String> rolesRequeridos, StreamObserver<?> responseObserver){
        if(!rolesRequeridos.contains(ContextKeys.ROLE_CONTENT_KEY.get())){
            responseObserver.onError(Status.PERMISSION_DENIED.withDescription("Acceso denegado").asRuntimeException());
            return false;
        }
        return true;
    }

    public String generarToken(String rol, String usuario){

        String token = Jwts.builder()
                .subject(usuario)
                .claim("rol",rol)
                .expiration(new Date(System.currentTimeMillis()+7200000))   //2 horas de validez
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes())
                .compact();
        return token;
    }
}
