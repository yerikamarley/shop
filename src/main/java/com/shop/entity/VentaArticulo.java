package com.shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "venta_articulos")
@Data
public class VentaArticulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sales_id", nullable = false)
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "item_id", nullable = false)
    private Articulo articulo;

    private Integer quantity;

    private Integer value;
}