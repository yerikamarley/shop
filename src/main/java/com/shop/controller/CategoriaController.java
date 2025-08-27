package com.shop.controller;

import com.shop.entity.Categoria;
import com.shop.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAll() {
        Map<String, Object> response = new HashMap<>();
        response.put("objeto", service.getAll());
        response.put("mensaje", "Categorias obtenidas correctamente");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            response.put("objeto", service.getById(id));
            response.put("mensaje", "Categoria obtenida correctamente");
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
    public ResponseEntity<Map<String, Object>> create(@RequestBody Categoria categoria) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCategoria(categoria, false);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        response.put("objeto", service.create(categoria));
        response.put("mensaje", "Categoria creada correctamente");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> update(@PathVariable Long id, @RequestBody Categoria categoria) {
        Map<String, Object> response = new HashMap<>();

        String error = validateCategoria(categoria, true);
        if (error != null) {
            response.put("objeto", null);
            response.put("mensaje", error);
            response.put("status", HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(response);
        }

        try {
            response.put("objeto", service.update(id, categoria));
            response.put("mensaje", "Categoria actualizada correctamente");
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
            response.put("mensaje", "Categoria eliminada correctamente");
            response.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("objeto", null);
            response.put("mensaje", e.getMessage());
            response.put("status", HttpStatus.NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    private String validateCategoria(Categoria categoria, boolean isUpdate) {
        if (!isUpdate) {
            if (categoria.getDescription() == null || categoria.getDescription().trim().isEmpty()) {
                return "La descripción es obligatoria y no puede estar vacía";
            }
        }
        if (categoria.getDescription() != null && categoria.getDescription().length() > 255) {
            return "La descripción no puede exceder 255 caracteres";
        }
        return null;
    }
}