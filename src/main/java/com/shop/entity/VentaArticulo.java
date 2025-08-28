package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // Esto hace que esta clase sea una tabla en la base de datos.
@Table(name = "venta_articulos") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"venta", "articulo"}) // Esto excluye las referencias para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"venta", "articulo"}) // Esto excluye las referencias para evitar problemas al comparar objetos.
public class VentaArticulo {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único de esta línea de venta.

    @ManyToOne // Esto define una relación muchos-a-uno: muchas líneas pertenecen a una venta.
    @JoinColumn(name = "sales_id", nullable = false) // Esto une con la columna "sales_id" en la DB, no puede ser nulo.
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Venta venta; // Esto referencia a la venta total.

    @ManyToOne // Esto define una relación muchos-a-uno: muchas líneas pertenecen a un articulo.
    @JoinColumn(name = "item_id", nullable = false) // Esto une con la columna "item_id".
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Articulo articulo; // Esto referencia al artículo vendido.

    @NotNull(message = "La cantidad es requerida") // Valida que no sea nulo.
    @Min(value = 1, message = "La cantidad debe ser al menos 1") // Valida mínimo 1.
    private Integer quantity; // Esto guarda la cantidad vendida.

    private Integer value; // Esto guarda el valor total de esta línea.
}