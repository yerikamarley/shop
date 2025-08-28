package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity // Esto hace que esta clase sea una tabla en la base de datos.
@Table(name = "caracteristicas") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"articuloCaracteristicas"}) // Esto excluye la lista para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"articuloCaracteristicas"}) // Esto excluye la lista para evitar problemas al comparar objetos.
public class Caracteristica {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único de la característica.

    @NotBlank(message = "La descripción es requerida") // Esto valida que la descripción no sea vacía.
    private String description; // Esto guarda la descripción de la característica, como "Color".

    @OneToMany(mappedBy = "caracteristica", cascade = CascadeType.ALL, orphanRemoval = true) // Esto define una relación uno-a-muchos: una característica tiene muchas relaciones.
    @JsonManagedReference // Esto maneja la serialización JSON del lado padre, evita ciclos.
    private List<ArticuloCaracteristica> articuloCaracteristicas; // Esto es la lista de relaciones con artículos.
}