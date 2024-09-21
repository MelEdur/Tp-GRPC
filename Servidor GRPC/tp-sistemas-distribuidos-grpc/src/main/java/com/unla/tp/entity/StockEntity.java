package com.unla.tp.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "stock_entity")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStock;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "idProducto", nullable = false)
    private ProductoEntity producto;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "idTienda", nullable = false)
    private TiendaEntity tienda;

    private int cantidad;

    private boolean habilitado;
}
