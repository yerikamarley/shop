package com.shop.controller;

import com.shop.entity.ArticuloCaracteristica;
import com.shop.service.ArticuloCaracteristicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/articulo-caracteristicas")
public class ArticuloCaracteristicaController {

    @Autowired
    private ArticuloCaracteristicaService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto", service.getAll());
        response.put("mensaje", "ArticuloCaracteristicas obtenidos correctamente");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("objeto", service.getById(id));
            response.put("mensaje", "ArticuloCaracteristica obtenido correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody ArticuloCaracteristica articuloCaracteristica) {
        Map<String, Object> response = new HashMap<>();

        String error = validateArticuloCaracteristica(articuloCaracteristica, false);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("objeto", service.create(articuloCaracteristica));
        response.put("mensaje", "ArticuloCaracteristica creado correctamente");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody ArticuloCaracteristica articuloCaracteristica) {
        Map<String, Object> response = new HashMap<>();

        String error = validateArticuloCaracteristica(articuloCaracteristica, true);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("objeto", service.update(id, articuloCaracteristica));
            response.put("mensaje", "ArticuloCaracteristica actualizado correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.delete(id);
            response.put("objeto", null);
            response.put("mensaje", "ArticuloCaracteristica eliminado correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private String validateArticuloCaracteristica(ArticuloCaracteristica articuloCaracteristica, boolean isUpdate) {
        if (!isUpdate) {
            if (articuloCaracteristica.getArticulo() == null || articuloCaracteristica.getArticulo().getId() == null) {
                return "El artículo es obligatorio";
            }
            if (articuloCaracteristica.getCaracteristica() == null || articuloCaracteristica.getCaracteristica().getId() == null) {
                return "La característica es obligatoria";
            }
            if (articuloCaracteristica.getValue() == null || articuloCaracteristica.getValue().trim().isEmpty()) {
                return "El valor es obligatorio y no puede estar vacío";
            }
        }
        if (articuloCaracteristica.getValue() != null && articuloCaracteristica.getValue().length() > 255) {
            return "El valor no puede exceder 255 caracteres";
        }
        return null;
    }
}