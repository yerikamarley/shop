package com.shop.controller;

import com.shop.dto.CompraArticuloDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.CompraArticuloService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/compra-articulos") // Ruta base para este controller.
public class CompraArticuloController {

    @Autowired // Inyecta el service automáticamente.
    private CompraArticuloService service;

    /**
     * Obtiene todas las líneas de compra.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "CompraArticulos obtenidos correctamente", "OK"));
    }

    /**
     * Obtiene una línea de compra por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "CompraArticulo obtenido correctamente", "OK"));
    }

    /**
     * Crea una nueva línea de compra.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody CompraArticuloDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "CompraArticulo creado correctamente", "OK"));
    }

    /**
     * Actualiza una línea de compra existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CompraArticuloDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "CompraArticulo actualizado correctamente", "OK"));
    }

    /**
     * Borra una línea de compra por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "CompraArticulo eliminado correctamente", "OK"));
    }
}