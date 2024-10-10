package com.unla.tp.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrdenRequest {
    public String codigo;
    public List<Item> items;
}
