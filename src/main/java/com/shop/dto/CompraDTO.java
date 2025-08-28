package com.shop.dto;


import jakarta.validation.constraints.NotNull;

import java.util.List;

public class CompraDTO {

    @NotNull(message = "El proveedor es requerido") // Valida no nulo.
    private Long providerId; // ID del proveedor (en vez de objeto).

    private Integer value; // Valor total (opcional, se calcula en service).

    private List<CompraArticuloDTO> articulos; // Lista de líneas (usa DTOs).

    // Getters y setters.
    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public List<CompraArticuloDTO> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<CompraArticuloDTO> articulos) {
        this.articulos = articulos;
    }
}