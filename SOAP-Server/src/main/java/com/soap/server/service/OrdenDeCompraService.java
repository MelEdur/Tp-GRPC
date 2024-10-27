package com.soap.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soap.server.entity.OrdenDeCompra;
import com.soap.server.repository.IOrdenDeCompraRepository;

@Service
public class OrdenDeCompraService {
    @Autowired
    private IOrdenDeCompraRepository ordenDeCompraRepository;


    public List<OrdenDeCompra> GetOrdenesDeCompraPorFiltro(){
        List<OrdenDeCompra> ordenesDeCompraResult = ordenDeCompraRepository.findAll();
        return ordenesDeCompraResult;
    };
}
