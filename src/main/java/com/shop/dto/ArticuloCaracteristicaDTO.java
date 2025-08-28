package com.shop.dto;

import jakarta.validation.constraints.NotBlank;

public class ArticuloCaracteristicaDTO {

    @NotBlank(message = "El valor es requerido") // Esto valida que no sea vacío.
    private String value; // Valor específico (e.g., "Rojo").

    private Long itemId; // ID del artículo (en vez de objeto).

    private Long characteristicId; // ID de la característica (en vez de objeto).

    // Getters y setters.
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCharacteristicId() {
        return characteristicId;
    }

    public void setCharacteristicId(Long characteristicId) {
        this.characteristicId = characteristicId;
    }
}