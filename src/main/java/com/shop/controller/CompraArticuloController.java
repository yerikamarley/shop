package com.shop.controller;

import com.shop.entity.CompraArticulo;
import com.shop.service.CompraArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/compra-articulos")
public class CompraArticuloController {

    @Autowired
    private CompraArticuloService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto", service.getAll());
        response.put("mensaje", "CompraArticulos obtenidos correctamente");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("objeto", service.getById(id));
            response.put("mensaje", "CompraArticulo obtenido correctamente");
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody CompraArticulo compraArticulo) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCompraArticulo(compraArticulo, false);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("objeto", service.create(compraArticulo));
        response.put("mensaje", "CompraArticulo creado correctamente");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody CompraArticulo compraArticulo) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCompraArticulo(compraArticulo, true);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("objeto", service.update(id, compraArticulo));
            response.put("mensaje", "CompraArticulo actualizado correctamente");
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
            response.put("mensaje", "CompraArticulo eliminado correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private String validateCompraArticulo(CompraArticulo compraArticulo, boolean isUpdate) {
        if (!isUpdate) {
            if (compraArticulo.getArticulo() == null || compraArticulo.getArticulo().getId() == null) {
                return "El artículo es obligatorio";
            }
            if (compraArticulo.getCompra() == null || compraArticulo.getCompra().getId() == null) {
                return "La compra es obligatoria";
            }
            if (compraArticulo.getQuantity() == null || compraArticulo.getQuantity() <= 0) {
                return "La cantidad debe ser positiva";
            }
            if (compraArticulo.getUniValue() == null || compraArticulo.getUniValue() <= 0) {
                return "El valor unitario debe ser positivo";
            }
            if (compraArticulo.getValue() == null || compraArticulo.getValue() <= 0) {
                return "El valor total debe ser positivo";
            }
        }
        if (compraArticulo.getQuantity() != null && compraArticulo.getQuantity() <= 0) {
            return "La cantidad debe ser positiva";
        }
        if (compraArticulo.getUniValue() != null && compraArticulo.getUniValue() <= 0) {
            return "El valor unitario debe ser positivo";
        }
        if (compraArticulo.getValue() != null && compraArticulo.getValue() <= 0) {
            return "El valor total debe ser positivo";
        }
        return null;
    }
}