package com.shop.dto;

import jakarta.validation.constraints.NotBlank;

public class CaracteristicaDTO {

    @NotBlank(message = "La descripción es requerida") // Esto valida que no sea vacío.
    private String description; // Descripción de la característica.

    // Getters y setters.
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}