package com.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "articulo_caracteristicas")
@Data
public class ArticuloCaracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "characteristic_id", nullable = false)
    private Caracteristica caracteristica;

    private String value;
}