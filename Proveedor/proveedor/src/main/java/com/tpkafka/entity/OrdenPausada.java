package com.tpkafka.entity;

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
public class OrdenPausada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrdenPausada;

    private String codigoTienda;

    private int idOrden;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_orden")
    private List<Item> items;

    private LocalDate fechaSolicitud;
}
