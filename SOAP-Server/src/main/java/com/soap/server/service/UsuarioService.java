package com.soap.server.service;

import com.soap.server.entity.TiendaEntity;
import com.soap.server.entity.UsuarioEntity;
import com.soap.server.repository.ITiendaRepository;
import com.soap.server.repository.IUsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import stockeate.AgregarUsuariosResponse;
import stockeate.Usuario;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository usuarioRepository;
    private final ITiendaRepository tiendaRepository;

    public AgregarUsuariosResponse agregarUsuarios(List<Usuario> usuarios){
        AgregarUsuariosResponse response = new AgregarUsuariosResponse();

        for(Usuario usuario : usuarios){

            Optional<TiendaEntity> tienda = tiendaRepository.findByCodigoTienda(usuario.getCodigoTienda());
            boolean error = false;

            if(camposIncompletos(usuario)){
                response.getErrores().add("Error en la linea "+usuario.getLinea()+" : No puede enviar campos vacíos");
                error = true;
            }
            if(tienda.isEmpty()){
                response.getErrores().add("Error en la linea "+usuario.getLinea()+" : No existe tienda con el código especificado");
                error = true;
            }
            if(tienda.isPresent() && !tienda.get().getHabilitada()){
                response.getErrores().add("Error en la linea "+usuario.getLinea()+" : La tienda especificada no se encuentra habilitada");
                error = true;
            }
            if(usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario()).isPresent()){
                response.getErrores().add("Error en la linea "+usuario.getLinea()+" : Ya existe un usuario con ese nombre");
                error = true;
            }
            //Si no hubo ningún error añado al usuario
            if(!error){
                usuarioRepository.save(UsuarioEntity.builder()
                                .nombreUsuario(usuario.getNombreUsuario())
                                .contrasenia(usuario.getContrasenia())
                                .nombre(usuario.getNombre())
                                .apellido(usuario.getApellido())
                                .codigoTienda(usuario.getCodigoTienda())
                                .habilitado(true)
                                .rol("USUARIO")
                        .build());
            }
        }

        return response;
    }

    public boolean camposIncompletos(Usuario usuario){
        return (usuario.getNombreUsuario().isBlank()
                || usuario.getContrasenia().isBlank()
                || usuario.getNombre().isBlank()
                || usuario.getApellido().isBlank()
                || usuario.getCodigoTienda().isBlank());
    }
}
