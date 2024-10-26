package com.soap.server.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "tienda_entity")
public class TiendaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    //@Unique probar
    private String codigoTienda;

    private String direccion;

    private String ciudad;

    private String provincia;

    private Boolean habilitada;

}
