package com.shop.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class VentaDTO {

    @NotNull(message = "El cliente es requerido") // Valida no nulo.
    private Long customerId; // ID del cliente (en vez de objeto).

    private Integer value; // Valor total (opcional, se calcula).

    private List<VentaArticuloDTO> articulos; // Lista de líneas (usa DTOs).

    // Getters y setters.
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<VentaArticuloDTO> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<VentaArticuloDTO> articulos) {
        this.articulos = articulos;
    }
}