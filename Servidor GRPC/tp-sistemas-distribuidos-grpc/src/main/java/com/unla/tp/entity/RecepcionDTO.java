package com.unla.tp.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecepcionDTO {
    private int idOrdenDeDespacho;
    private LocalDate fechaDeRecepcion;
}
