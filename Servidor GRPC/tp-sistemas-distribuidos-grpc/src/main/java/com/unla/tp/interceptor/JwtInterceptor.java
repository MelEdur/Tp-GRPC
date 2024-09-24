package com.unla.tp.interceptor;

import com.unla.tp.util.ContextKeys;
import io.grpc.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.lognet.springboot.grpc.GRpcGlobalInterceptor;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@RequiredArgsConstructor
//@GRpcGlobalInterceptor
public class JwtInterceptor implements ServerInterceptor {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        //Extraemos nombre del método
        String metodo = serverCall.getMethodDescriptor().getFullMethodName();

        //Lista de métodos que no necesitan logeo
        List<String> metodosExcluidos = List.of(
                "AuthService/Login"
        );

        if (metodosExcluidos.contains(metodo)){
            return Contexts.interceptCall(Context.current(),serverCall,metadata,serverCallHandler);
        }


        //Extraemos el token
        String token = metadata.get(Metadata.Key.of("Authorization",Metadata.ASCII_STRING_MARSHALLER));

        if(token != null && token.startsWith("Bearer ")){
            token = token.substring(7);

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String usuario = claims.getSubject();

                //Extraer roles
                String rol = claims.get("rol", String.class);


                Context context = Context.current()
                        .withValues(ContextKeys.ROLE_CONTENT_KEY,rol,ContextKeys.USER_CONTENT_KEY,usuario);
                return  Contexts.interceptCall(context,serverCall,metadata,serverCallHandler);
            }catch (Exception e){
                serverCall.close(Status.UNAUTHENTICATED.withDescription("Token invalido"),metadata);
                return new ServerCall.Listener<ReqT>() {};
            }
        }
        else {
            serverCall.close(Status.UNAUTHENTICATED.withDescription("No se ha encontrado token"),metadata);
            return new ServerCall.Listener<ReqT>() {};
        }
    }
}
