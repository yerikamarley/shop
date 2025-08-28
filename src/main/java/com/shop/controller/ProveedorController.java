package com.shop.controller;

import com.shop.dto.ProveedorDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.ProveedorService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/proveedores") // Ruta base para este controller.
public class ProveedorController {

    @Autowired
    private ProveedorService service;

    /**
     * Obtiene todos los proveedores.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "Proveedores obtenidos correctamente", "OK"));
    }

    /**
     * Obtiene un proveedor por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "Proveedor obtenido correctamente", "OK"));
    }

    /**
     * Crea un nuevo proveedor.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody ProveedorDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "Proveedor creado correctamente", "OK"));
    }

    /**
     * Actualiza un proveedor existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ProveedorDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "Proveedor actualizado correctamente", "OK"));
    }

    /**
     * Borra un proveedor por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "Proveedor eliminado correctamente", "OK"));
    }
}