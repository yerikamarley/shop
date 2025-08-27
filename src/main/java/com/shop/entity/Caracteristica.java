package com.shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "caracteristicas")
@Data
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(mappedBy = "caracteristica", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ArticuloCaracteristica> articuloCaracteristicas;
}