package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity // Esto hace que esta clase sea una tabla en la base de datos.
@Table(name = "articulos") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"caracteristicas", "comprasArticulos", "ventasArticulos"}) // Esto excluye las listas para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"caracteristicas", "comprasArticulos", "ventasArticulos"}) // Esto excluye las listas para evitar problemas al comparar objetos.
public class Articulo {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único del artículo.

    @NotBlank(message = "El nombre es requerido") // Esto valida que el nombre no sea vacío.
    private String name; // Esto guarda el nombre del artículo.

    private String description; // Esto guarda la descripción (opcional).

    @ManyToOne // Esto define una relación muchos-a-uno: muchos articulos pertenecen a una categoria.
    @JoinColumn(name = "category_id", nullable = false) // Esto une con la columna "category_id" en la DB, no puede ser nulo.
    @NotNull(message = "La categoría es requerida") // Valida no nulo.
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Categoria categoria; // Esto referencia a la categoría.

    @Min(value = 0, message = "La cantidad no puede ser negativa") // Valida mínimo 0.
    private Integer quantity; // Esto guarda la cantidad en stock.

    private Integer state; // Esto guarda el estado (e.g., 1=activo).

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true) // Relación con características.
    @JsonManagedReference // Esto maneja la serialización JSON del lado padre, evita ciclos.
    private List<ArticuloCaracteristica> caracteristicas; // Lista de características del artículo.

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true) // Relación con líneas de compra.
    @JsonManagedReference // Esto maneja la serialización JSON del lado padre, evita ciclos.
    private List<CompraArticulo> comprasArticulos; // Lista de compras donde aparece este artículo.

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true) // Relación con líneas de venta.
    @JsonManagedReference // Esto maneja la serialización JSON del lado padre, evita ciclos.
    private List<VentaArticulo> ventasArticulos; // Lista de ventas donde aparece este artículo.
}