package com.shop.controller;

import com.shop.entity.VentaArticulo;
import com.shop.service.VentaArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/venta-articulos")
public class VentaArticuloController {

    @Autowired
    private VentaArticuloService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto", service.getAll());
        response.put("mensaje", "VentaArticulos obtenidos correctamente");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("objeto", service.getById(id));
            response.put("mensaje", "VentaArticulo obtenido correctamente");
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody VentaArticulo ventaArticulo) {
        Map<String, Object> response = new HashMap<>();

        String error = validateVentaArticulo(ventaArticulo, false);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("objeto", service.create(ventaArticulo));
        response.put("mensaje", "VentaArticulo creado correctamente");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody VentaArticulo ventaArticulo) {
        Map<String, Object> response = new HashMap<>();

        String error = validateVentaArticulo(ventaArticulo, true);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("objeto", service.update(id, ventaArticulo));
            response.put("mensaje", "VentaArticulo actualizado correctamente");
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
            response.put("mensaje", "VentaArticulo eliminado correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private String validateVentaArticulo(VentaArticulo ventaArticulo, boolean isUpdate) {
        if (!isUpdate) {
            if (ventaArticulo.getVenta() == null || ventaArticulo.getVenta().getId() == null) {
                return "La venta es obligatoria";
            }
            if (ventaArticulo.getArticulo() == null || ventaArticulo.getArticulo().getId() == null) {
                return "El artículo es obligatorio";
            }
            if (ventaArticulo.getQuantity() == null || ventaArticulo.getQuantity() <= 0) {
                return "La cantidad debe ser positiva";
            }
            if (ventaArticulo.getValue() == null || ventaArticulo.getValue() <= 0) {
                return "El valor debe ser positivo";
            }
        }
        if (ventaArticulo.getQuantity() != null && ventaArticulo.getQuantity() <= 0) {
            return "La cantidad debe ser positiva";
        }
        if (ventaArticulo.getValue() != null && ventaArticulo.getValue() <= 0) {
            return "El valor debe ser positivo";
        }
        return null;
    }
}