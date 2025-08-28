package com.shop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CompraArticuloDTO {

    @NotNull(message = "El artículo es requerido") // Valida no nulo.
    private Long itemId; // ID del artículo (en vez de objeto).

    @NotNull(message = "La cantidad es requerida") // Valida no nulo.
    @Min(value = 1, message = "La cantidad debe ser al menos 1") // Valida mínimo 1.
    private Integer quantity; // Cantidad.

    private Integer uniValue; // Valor unitario (opcional).

    private Integer value; // Valor total (opcional, se calcula).

    // Getters y setters.
    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getUniValue() {
        return uniValue;
    }

    public void setUniValue(Integer uniValue) {
        this.uniValue = uniValue;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

	public Long getPurchaseId() {
		// TODO Auto-generated method stub
		return null;
	}
}