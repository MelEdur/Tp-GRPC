package com.unla.tp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "producto_entity")
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String codigoProducto;

    private String nombreProducto;

    private String talle;

    private String color;

    private String foto;

    private boolean habilitado;
}