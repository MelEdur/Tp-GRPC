package com.unla.tp.entity;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrdenDeDespachoResponse {

    private int idOrdenDeCompra;

    private LocalDate fechaEstimada;

    private int idOrdenDeDespacho;
}
