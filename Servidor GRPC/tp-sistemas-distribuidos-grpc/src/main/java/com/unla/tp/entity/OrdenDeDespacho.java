package com.unla.tp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdenDeDespacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idOrdenDeDespacho;

    private int idOrdenDeCompra;

    private LocalDate fechaEstimada;
}

