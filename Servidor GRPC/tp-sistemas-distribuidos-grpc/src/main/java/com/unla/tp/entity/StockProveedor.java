package com.unla.tp.entity;

import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "stock_proveedor")
public class StockProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStock;

    private String codigo;

    @ElementCollection
    private List<String> colores;

    @ElementCollection
    private List<String> talles;

    @ElementCollection
    private List<String> fotos;

    private int cantidad;
}
