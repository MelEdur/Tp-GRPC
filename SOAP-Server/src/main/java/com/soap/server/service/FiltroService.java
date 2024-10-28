package com.soap.server.service;

import com.soap.server.entity.Filtro;
import com.soap.server.entity.UsuarioEntity;
import com.soap.server.repository.IFiltroRepository;
import com.soap.server.repository.IUsuarioRepository;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import stockeate.TraerFiltrosResponse;
import stockeate.GuardarFiltroResponse;
import stockeate.EditarFiltroResponse;
import stockeate.EliminarFiltroResponse;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FiltroService {

        private final IFiltroRepository filtroRepository;
        private final IUsuarioRepository usuarioRepository;

        @Transactional
        public TraerFiltrosResponse traerFiltros(int idUsuario) {
                TraerFiltrosResponse response = new TraerFiltrosResponse();

                // Mapear los filtros con for each
                List<Filtro> listaFiltros = usuarioRepository.findById(idUsuario).get().getFiltros();// lista<Filtros>

                for (Filtro filtro : listaFiltros) {
                        stockeate.Filtro filtroAux = new stockeate.Filtro();
                        filtroAux.setIdFiltro(filtro.getId());
                        filtroAux.setCodigoProducto(filtro.getCodigoProducto());
                        filtroAux.setFechaDesde(filtro.getFechaDesde().toString());
                        filtroAux.setFechaHasta(filtro.getFechaHasta().toString());
                        filtroAux.setEstado(filtro.getEstado());
                        filtroAux.setCodigoTienda(filtro.getCodigoTienda());

                        response.getFiltros().add(filtroAux);

                }
                return response;
        }

        @Transactional
        public GuardarFiltroResponse guardarFiltro(String codigoProducto, String fechaDesde, String fechaHasta, String estado, String codigoTienda, int idUsuario) {

                GuardarFiltroResponse response = new GuardarFiltroResponse();

                // Convierto las fechas String en LocalDate con el formato deseado
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate fechaDesdeLocaldate = LocalDate.parse(fechaDesde, formatter);
                LocalDate fechaHastaLocaldate = LocalDate.parse(fechaHasta, formatter);

                // Buscar el usuario por idUsuario
                Optional<UsuarioEntity> usuarioOptional = usuarioRepository.findById(idUsuario);
                UsuarioEntity usuario = usuarioOptional.get();

                // Crear un nuevo filtro con los datos proporcionados y asociarlo al usuario
                Filtro nuevoFiltro = Filtro.builder()
                                .codigoProducto(codigoProducto)
                                .fechaDesde(fechaDesdeLocaldate)
                                .fechaHasta(fechaHastaLocaldate)
                                .estado(estado)
                                .codigoTienda(codigoTienda)
                                .build();

                // Guarda el filtro en la base de datos
                Filtro filtroGuardado = filtroRepository.save(nuevoFiltro);
                
                List<Filtro> listaFiltros = usuario.getFiltros();
                listaFiltros.add(nuevoFiltro);
                usuario.setFiltros(listaFiltros);
                usuarioRepository.save(usuario);

                // Mapeo a xsd
                stockeate.Filtro filtroAux = new stockeate.Filtro();
                filtroAux.setIdFiltro(filtroGuardado.getId());
                filtroAux.setCodigoProducto(filtroGuardado.getCodigoProducto());
                filtroAux.setFechaDesde(filtroGuardado.getFechaDesde().toString());
                filtroAux.setFechaHasta(filtroGuardado.getFechaHasta().toString());
                filtroAux.setEstado(filtroGuardado.getEstado());
                filtroAux.setCodigoTienda(filtroGuardado.getCodigoTienda());
                
                response.setFiltro(filtroAux);

                return response;
        }

        @Transactional
        public EditarFiltroResponse editarFiltro(String codigoProducto, String fechaDesde, String fechaHasta, String estado, String codigoTienda, int idFiltro) {

                EditarFiltroResponse response = new EditarFiltroResponse();

                // Convierto las fechas String en LocalDate con el formato deseado
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

                LocalDate fechaDesdeLocaldate = LocalDate.parse(fechaDesde, formatter);
                LocalDate fechaHastaLocaldate = LocalDate.parse(fechaHasta, formatter);

                // Busca el filtro por idFiltro
                Optional<Filtro> filtroOptional = filtroRepository.findById(idFiltro);

                // Si el filtro existe, actualiza sus valores
                Filtro filtro = filtroOptional.get();
                filtro.setCodigoProducto(codigoProducto);
                filtro.setFechaDesde(fechaDesdeLocaldate);
                filtro.setFechaHasta(fechaHastaLocaldate);
                filtro.setEstado(estado);
                filtro.setCodigoTienda(codigoTienda);

                // Guarda los cambios en la base de datos
                filtroRepository.save(filtro);

                // Configura un mensaje de éxito en la respuesta
                response.setMensaje("Filtro editado correctamente.");

                return response;
        }

        @Transactional
        public EliminarFiltroResponse eliminarFiltro(int idFiltro) {
                EliminarFiltroResponse response = new EliminarFiltroResponse();
                Filtro filtro = filtroRepository.findById(idFiltro).get();
                filtroRepository.delete(filtro);
                return response;
        }

}
