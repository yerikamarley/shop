package com.shop.controller;

import com.shop.dto.CompraDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.CompraService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/compras") // Ruta base para este controller.
public class CompraController {

    @Autowired // Inyecta el service automáticamente.
    private CompraService service;

    /**
     * Obtiene todas las compras.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "Compras obtenidas correctamente", "OK"));
    }

    /**
     * Obtiene una compra por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "Compra obtenida correctamente", "OK"));
    }

    /**
     * Crea una nueva compra.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody CompraDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "Compra creada correctamente", "OK"));
    }

    /**
     * Actualiza una compra existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CompraDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "Compra actualizada correctamente", "OK"));
    }

    /**
     * Borra una compra por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "Compra eliminada correctamente", "OK"));
    }
}