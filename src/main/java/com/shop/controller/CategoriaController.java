package com.shop.controller;

import com.shop.dto.CategoriaDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/categorias") // Ruta base para este controller.
public class CategoriaController {

    @Autowired // Inyecta el service automáticamente.
    private CategoriaService service;

    /**
     * Obtiene todas las categorías.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "Categorias obtenidas correctamente", "OK"));
    }

    /**
     * Obtiene una categoría por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "Categoria obtenida correctamente", "OK"));
    }

    /**
     * Crea una nueva categoría.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody CategoriaDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "Categoria creada correctamente", "OK"));
    }

    /**
     * Actualiza una categoría existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "Categoria actualizada correctamente", "OK"));
    }

    /**
     * Borra una categoría por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "Categoria eliminada correctamente", "OK"));
    }
}