package com.tpkafka.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class OrdenDTO {
    int idOrdenDeCompra;
    String codigoTienda;
    List<Item> items;
    LocalDate fechaSolicitud;
}