package com.soap.server.entity;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "catalogo_entity")
public class CatalogoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;

    @ManyToMany (fetch = FetchType.EAGER)
    private List<ProductoEntity> productos;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "idTienda")
    private TiendaEntity tienda;

}
