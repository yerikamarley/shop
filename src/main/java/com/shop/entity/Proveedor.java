package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity // Esto hace que esta clase sea una tabla en la base de datos.
@Table(name = "proveedores") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"compras"}) // Esto excluye la lista para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"compras"}) // Esto excluye la lista para evitar problemas al comparar objetos.
public class Proveedor {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único del proveedor.

    @NotBlank(message = "El nombre es requerido") // Esto valida que no sea vacío.
    private String name; // Esto guarda el nombre del proveedor.

    private String address; // Esto guarda la dirección (opcional).

    private String phone; // Esto guarda el teléfono (opcional).

    @Email(message = "Debe ser un email válido") // Esto valida el formato de email.
    private String email; // Esto guarda el email (opcional).

    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true) // Esto define una relación uno-a-muchos: un proveedor tiene muchas compras.
    @JsonManagedReference // Esto maneja la serialización JSON del lado padre, evita ciclos.
    private List<Compra> compras; // Esto es la lista de compras asociadas.
}