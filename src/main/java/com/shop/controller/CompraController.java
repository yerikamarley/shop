package com.shop.controller;

import com.shop.entity.Compra;
import com.shop.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    @Autowired
    private CompraService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto", service.getAll());
        response.put("mensaje", "Compras obtenidas correctamente");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("objeto", service.getById(id));
            response.put("mensaje", "Compra obtenida correctamente");
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody Compra compra) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCompra(compra, false);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("objeto", service.create(compra));
        response.put("mensaje", "Compra creada correctamente");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Compra compra) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCompra(compra, true);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("objeto", service.update(id, compra));
            response.put("mensaje", "Compra actualizada correctamente");
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
            response.put("mensaje", "Compra eliminada correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private String validateCompra(Compra compra, boolean isUpdate) {
        if (!isUpdate) {
            if (compra.getTime() == null || compra.getTime().isAfter(LocalDateTime.now())) {
                return "La fecha es obligatoria y no puede ser futura";
            }
            if (compra.getProveedor() == null || compra.getProveedor().getId() == null) {
                return "El proveedor es obligatorio";
            }
            if (compra.getValue() == null || compra.getValue() <= 0) {
                return "El valor debe ser positivo";
            }
        }
        if (compra.getTime() != null && compra.getTime().isAfter(LocalDateTime.now())) {
            return "La fecha no puede ser futura";
        }
        if (compra.getValue() != null && compra.getValue() <= 0) {
            return "El valor debe ser positivo";
        }
        return null;
    }
}