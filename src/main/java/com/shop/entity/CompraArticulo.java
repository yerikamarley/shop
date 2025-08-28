package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // Esto hace que esta clase sea una tabla en la base de datos.
@Table(name = "compra_articulos") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"articulo", "compra"}) // Esto excluye las referencias para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"articulo", "compra"}) // Esto excluye las referencias para evitar problemas al comparar objetos.
public class CompraArticulo {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único de esta línea de compra.

    @ManyToOne // Esto define una relación muchos-a-uno: muchas CompraArticulo pertenecen a un Articulo.
    @JoinColumn(name = "item_id", nullable = false) // Esto une con la columna "item_id" en la DB, no puede ser nulo.
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Articulo articulo; // Esto referencia al artículo comprado.

    @NotNull(message = "La cantidad es requerida") // Valida que no sea nulo.
    @Min(value = 1, message = "La cantidad debe ser al menos 1") // Valida mínimo 1.
    private Integer quantity; // Esto guarda la cantidad comprada.

    @ManyToOne // Esto define una relación muchos-a-uno: muchas CompraArticulo pertenecen a una Compra.
    @JoinColumn(name = "purchase_id", nullable = false) // Esto une con la columna "purchase_id".
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Compra compra; // Esto referencia a la compra total.

    private Integer uniValue; // Esto guarda el valor unitario (precio por uno).

    private Integer value; // Esto guarda el valor total (cantidad * uniValue).
}