package com.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "compra_articulos")
@Data
public class CompraArticulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Articulo articulo;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false)
    private Compra compra;

    private Integer uniValue;

    private Integer value;
}