package com.shop.controller;

import com.shop.dto.ArticuloCaracteristicaDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.ArticuloCaracteristicaService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/articulo-caracteristicas") // Ruta base para este controller.
public class ArticuloCaracteristicaController {

    @Autowired // Inyecta el service automáticamente.
    private ArticuloCaracteristicaService service;

    /**
     * Obtiene todas las relaciones artículo-característica.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "ArticuloCaracteristicas obtenidos correctamente", "OK"));
    }

    /**
     * Obtiene una relación por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "ArticuloCaracteristica obtenido correctamente", "OK"));
    }

    /**
     * Crea una nueva relación.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody ArticuloCaracteristicaDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "ArticuloCaracteristica creado correctamente", "OK"));
    }

    /**
     * Actualiza una relación existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ArticuloCaracteristicaDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "ArticuloCaracteristica actualizado correctamente", "OK"));
    }

    /**
     * Borra una relación por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "ArticuloCaracteristica eliminado correctamente", "OK"));
    }
}