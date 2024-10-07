package com.tpkafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class DespachoResponse {

    private int idOrdenDeDespacho;
    private int idOrdenDeCompra;
    private LocalDate fechaEstimada;
}
