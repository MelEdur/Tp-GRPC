package com.unla.tp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unla.tp.entity.StockProveedor;
import com.unla.tp.repository.IStockProveedorRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin()
public class ProductoProveedorController {

    private final IStockProveedorRepository stockProveedorRepository;
    
    //@CrossOrigin("http://localhost:8000")
    @GetMapping("/producto/proveedor")
    public List<StockProveedor> productosNuevosProveedor(){

        List<StockProveedor> listaProductosNuevos = new ArrayList<>();
        listaProductosNuevos = stockProveedorRepository.findAll();

        return listaProductosNuevos;
    }
}
