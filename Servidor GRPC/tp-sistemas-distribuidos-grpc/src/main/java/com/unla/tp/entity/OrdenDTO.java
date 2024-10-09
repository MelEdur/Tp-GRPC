package com.unla.tp.entity;

import java.time.LocalDate;
import java.util.List;

import com.google.type.DateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdenDTO {
    int idOrdenDeCompra;
    String codigoTienda;
    List<Item> items;
    LocalDate fechaSolicitud;
}
