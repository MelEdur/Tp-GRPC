package com.tpkafka.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idStock;

    private String codigo;

    @ElementCollection
    private List<String> colores;

    @ElementCollection
    private List<String> talles;

    @ElementCollection
    private List<String> fotos;

    private int cantidad;
}
