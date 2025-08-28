package com.shop.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {

    @NotBlank(message = "El nombre es requerido") // Esto valida que no sea vacío.
    private String name; // Nombre del cliente.

    private String address; // Dirección (opcional).

    private String phone; // Teléfono (opcional).

    @Email(message = "Debe ser un email válido") // Esto valida el formato de email.
    private String email; // Email (opcional).

    // Getters y setters.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}