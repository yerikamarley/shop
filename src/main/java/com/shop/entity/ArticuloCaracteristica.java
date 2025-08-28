package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity // Esto hace que esta clase sea una tabla en la base de datos.
@Table(name = "articulo_caracteristicas") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"articulo", "caracteristica"}) // Esto excluye las referencias para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"articulo", "caracteristica"}) // Esto excluye las referencias para evitar problemas al comparar objetos.
public class ArticuloCaracteristica {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único de la relación.

    @ManyToOne // Esto define una relación muchos-a-uno: muchas características pertenecen a un articulo.
    @JoinColumn(name = "item_id", nullable = false) // Esto une con la columna "item_id" en la DB, no puede ser nulo.
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Articulo articulo; // Esto referencia al artículo.

    @ManyToOne // Esto define una relación muchos-a-uno: muchas características pertenecen a una característica base.
    @JoinColumn(name = "characteristic_id", nullable = false) // Esto une con la columna "characteristic_id".
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Caracteristica caracteristica; // Esto referencia a la característica.

    @NotBlank(message = "El valor es requerido") // Esto valida que el valor no sea vacío.
    private String value; // Esto guarda el valor específico, como "Rojo".
}