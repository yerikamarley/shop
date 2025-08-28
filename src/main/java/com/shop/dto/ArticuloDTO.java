package com.shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ArticuloDTO {

    @NotBlank(message = "El nombre es requerido") // Esto valida que no sea vacío.
    private String name; // Nombre del artículo.

    private String description; // Descripción (opcional).

    @NotNull(message = "La categoría es requerida") // Valida no nulo.
    private Long categoryId; // ID de la categoría (en vez de objeto completo).

    @Min(value = 0, message = "La cantidad no puede ser negativa") // Valida mínimo 0.
    private Integer quantity; // Cantidad en stock.

    private Integer state; // Estado (e.g., 1=activo).

    // Getters y setters.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}