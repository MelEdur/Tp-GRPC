package com.tpkafka.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrdenDTO {
    int idOrdenDeCompra;
    String codigoTienda;
    List<Item> items;
    LocalDate fechaSolicitud;
}