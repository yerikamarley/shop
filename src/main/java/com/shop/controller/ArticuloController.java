package com.shop.controller;

import com.shop.dto.ArticuloDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.ArticuloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/articulos") // Ruta base para este controller.
public class ArticuloController {

    @Autowired // Inyecta el service automáticamente.
    private ArticuloService service;

    /**
     * Obtiene todos los artículos.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "Artículos obtenidos correctamente", "OK"));
    }

    /**
     * Obtiene un artículo por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "Artículo obtenido correctamente", "OK"));
    }

    /**
     * Crea un nuevo artículo.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody ArticuloDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "Artículo creado correctamente", "OK"));
    }

    /**
     * Actualiza un artículo existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ArticuloDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "Artículo actualizado correctamente", "OK"));
    }

    /**
     * Borra un artículo por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "Artículo eliminado correctamente", "OK"));
    }
}