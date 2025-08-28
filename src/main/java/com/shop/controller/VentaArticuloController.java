package com.shop.controller;

import com.shop.dto.VentaArticuloDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.VentaArticuloService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/venta-articulos") // Ruta base para este controller.
public class VentaArticuloController {

    @Autowired // Inyecta el service automáticamente.
    private VentaArticuloService service;

    /**
     * Obtiene todas las líneas de venta.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "VentaArticulos obtenidos correctamente", "OK"));
    }

    /**
     * Obtiene una línea de venta por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "VentaArticulo obtenido correctamente", "OK"));
    }

    /**
     * Crea una nueva línea de venta.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody VentaArticuloDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "VentaArticulo creado correctamente", "OK"));
    }

    /**
     * Actualiza una línea de venta existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody VentaArticuloDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "VentaArticulo actualizado correctamente", "OK"));
    }

    /**
     * Borra una línea de venta por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "VentaArticulo eliminado correctamente", "OK"));
    }
}