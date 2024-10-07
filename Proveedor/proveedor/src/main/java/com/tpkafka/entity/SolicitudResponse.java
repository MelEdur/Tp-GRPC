package com.tpkafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolicitudResponse {

    private String estado;
    private String observaciones;
    private int idOrdenDeCompra;
}
