package com.shop.controller;

import com.shop.entity.Proveedor;
import com.shop.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto", service.getAll());
        response.put("mensaje", "Proveedores obtenidos correctamente");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("objeto", service.getById(id));
            response.put("mensaje", "Proveedor obtenido correctamente");
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody Proveedor proveedor) {
        Map<String, Object> response = new HashMap<>();

        String error = validateProveedor(proveedor, false);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("objeto", service.create(proveedor));
        response.put("mensaje", "Proveedor creado correctamente");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Proveedor proveedor) {
        Map<String, Object> response = new HashMap<>();

        String error = validateProveedor(proveedor, true);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("objeto", service.update(id, proveedor));
            response.put("mensaje", "Proveedor actualizado correctamente");
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
            response.put("mensaje", "Proveedor eliminado correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private String validateProveedor(Proveedor proveedor, boolean isUpdate) {
        if (!isUpdate) {
            if (proveedor.getName() == null || proveedor.getName().trim().isEmpty()) {
                return "El nombre es obligatorio y no puede estar vacío";
            }
            if (proveedor.getAddress() == null || proveedor.getAddress().trim().isEmpty()) {
                return "La dirección es obligatoria y no puede estar vacía";
            }
            if (proveedor.getPhone() == null || proveedor.getPhone().trim().isEmpty()) {
                return "El teléfono es obligatorio y no puede estar vacío";
            }
            if (proveedor.getEmail() == null || proveedor.getEmail().trim().isEmpty() || !proveedor.getEmail().contains("@")) {
                return "El email es obligatorio y debe ser válido";
            }
        }
        if (proveedor.getName() != null && proveedor.getName().length() > 100) {
            return "El nombre no puede exceder 100 caracteres";
        }
        if (proveedor.getAddress() != null && proveedor.getAddress().length() > 255) {
            return "La dirección no puede exceder 255 caracteres";
        }
        if (proveedor.getPhone() != null && proveedor.getPhone().length() > 20) {
            return "El teléfono no puede exceder 20 caracteres";
        }
        if (proveedor.getEmail() != null && (!proveedor.getEmail().contains("@") || proveedor.getEmail().length() > 100)) {
            return "El email debe ser válido y no exceder 100 caracteres";
        }
        return null;
    }
}