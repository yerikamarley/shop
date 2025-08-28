package com.shop.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoriaDTO {

    @NotBlank(message = "La descripción es requerida") // Esto valida que no sea vacío.
    private String description; // Descripción de la categoría.

    // Getters y setters.
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}