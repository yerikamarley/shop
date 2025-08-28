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
@Table(name = "clientes") // Esto define el nombre de la tabla en la DB.
@Data // Esto genera automáticamente getters, setters, toString, equals y hashCode (de Lombok).
@ToString(exclude = {"ventas"}) // Esto excluye la lista para evitar ciclos infinitos al imprimir el objeto.
@EqualsAndHashCode(exclude = {"ventas"}) // Esto excluye la lista para evitar problemas al comparar objetos.
public class Cliente {

    @Id // Esto marca el campo como la clave primaria (ID único).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Esto hace que el ID se genere automáticamente (auto-incremento en MySQL).
    private Long id; // Esto es el ID único del cliente.

    @NotBlank(message = "El nombre es requerido") // Esto valida que el nombre no sea vacío.
    private String name; // Esto guarda el nombre del cliente.

    private String address; // Esto guarda la dirección (opcional).

    private String phone; // Esto guarda el teléfono (puedes agregar validaciones como @Pattern para formato).

    @Email(message = "Debe ser un email válido") // Esto valida el formato de email.
    private String email; // Esto guarda el email del cliente.

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true) // Esto define una relación uno-a-muchos: un cliente tiene muchas ventas.
    @JsonManagedReference // Esto maneja la serialización JSON del lado padre, evita ciclos.
    private List<Venta> ventas; // Esto es la lista de ventas hechas por este cliente.
}