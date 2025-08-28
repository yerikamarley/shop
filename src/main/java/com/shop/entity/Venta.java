package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity // Esto hace que esta clase sea una tabla en la base de datos.
@Table(name = "ventas") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"articulos"}) // Esto excluye la lista para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"articulos"}) // Esto excluye la lista para evitar problemas al comparar objetos.
public class Venta {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único de la venta.

    private LocalDateTime time; // Esto guarda la fecha y hora de la venta.

    @ManyToOne // Esto define una relación muchos-a-uno: muchas ventas pertenecen a un cliente.
    @JoinColumn(name = "customer_id", nullable = false) // Esto une con la columna "customer_id" en la DB, no puede ser nulo.
    @NotNull(message = "El cliente es requerido") // Valida no nulo.
    @JsonBackReference // Esto evita ciclos al convertir a JSON.
    private Cliente cliente; // Esto referencia al cliente.

    private Integer value; // Esto guarda el valor total de la venta.

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true) // Esto define una relación uno-a-muchos: una venta tiene muchas líneas.
    @JsonManagedReference // Esto maneja la serialización JSON del lado padre, evita ciclos.
    private List<VentaArticulo> articulos; // Esto es la lista de artículos vendidos en esta venta.
}