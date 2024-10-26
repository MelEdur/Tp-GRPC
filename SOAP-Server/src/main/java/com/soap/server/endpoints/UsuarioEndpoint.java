package com.soap.server.endpoints;


import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import stockeate.AgregarUsuariosRequest;
import stockeate.AgregarUsuariosResponse;

@Endpoint
public class UsuarioEndpoint {

    private static final String NAMESPACE_URI = "http://stockeate";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "agregarUsuariosRequest")
    @ResponsePayload
    public AgregarUsuariosResponse agregarUsuarios(@RequestPayload AgregarUsuariosRequest request){
        AgregarUsuariosResponse response = new AgregarUsuariosResponse();
        response.getErrores().add("Este es un error");
        return response;
    }
}
