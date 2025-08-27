package com.shop.controller;

import com.shop.entity.Cliente;
import com.shop.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto", service.getAll());
        response.put("mensaje", "Clientes obtenidos correctamente");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("objeto", service.getById(id));
            response.put("mensaje", "Cliente obtenido correctamente");
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCliente(cliente, false);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("objeto", service.create(cliente));
        response.put("mensaje", "Cliente creado correctamente");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCliente(cliente, true);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("objeto", service.update(id, cliente));
            response.put("mensaje", "Cliente actualizado correctamente");
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
            response.put("mensaje", "Cliente eliminado correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private String validateCliente(Cliente cliente, boolean isUpdate) {
        if (!isUpdate) {
            if (cliente.getName() == null || cliente.getName().trim().isEmpty()) {
                return "El nombre es obligatorio y no puede estar vacío";
            }
            if (cliente.getAddress() == null || cliente.getAddress().trim().isEmpty()) {
                return "La dirección es obligatoria y no puede estar vacía";
            }
            if (cliente.getPhone() == null || cliente.getPhone().trim().isEmpty()) {
                return "El teléfono es obligatorio y no puede estar vacío";
            }
            if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
                return "El email es obligatorio y no puede estar vacío";
            }
        }
        if (cliente.getName() != null && cliente.getName().length() > 100) {
            return "El nombre no puede exceder 100 caracteres";
        }
        if (cliente.getAddress() != null && cliente.getAddress().length() > 255) {
            return "La dirección no puede exceder 255 caracteres";
        }
        if (cliente.getPhone() != null && cliente.getPhone().length() > 20) {
            return "El teléfono no puede exceder 20 caracteres";
        }
        if (cliente.getEmail() != null) {
            if (cliente.getEmail().length() > 100) {
                return "El email no puede exceder 100 caracteres";
            }
            if (!cliente.getEmail().contains("@") || !cliente.getEmail().contains(".")) {
                return "Debe ser un email válido";
            }
        }
        return null;
    }
}