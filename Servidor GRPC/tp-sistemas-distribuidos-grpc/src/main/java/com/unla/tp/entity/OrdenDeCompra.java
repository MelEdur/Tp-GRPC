package com.unla.tp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orden_de_compra")
@ToString
public class OrdenDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrdenDeCompra;

    private String codigoTienda;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_orden")
    private List<Item> items;

    private LocalDate fechaDeSolicitud;

    private String estado;

    private String observaciones;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_orden_de_despacho")
    private OrdenDeDespacho ordenDeDespacho;

    private LocalDate fechaDeRecepcion;
}
