package com.soap.server.endpoints;


import com.soap.server.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stockeate.AgregarUsuariosRequest;
import stockeate.AgregarUsuariosResponse;

@Endpoint
@RequiredArgsConstructor
public class UsuarioEndpoint {

    private static final String NAMESPACE_URI = "http://stockeate";
    private final UsuarioService usuarioService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "agregarUsuariosRequest")
    @ResponsePayload
    public AgregarUsuariosResponse agregarUsuarios(@RequestPayload AgregarUsuariosRequest request){
        return usuarioService.agregarUsuarios(request.getUsuarios());
    }
}
