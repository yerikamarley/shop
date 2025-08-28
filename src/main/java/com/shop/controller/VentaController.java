package com.shop.controller;

import com.shop.dto.VentaDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.VentaService;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/ventas") // Ruta base para este controller.
public class VentaController {

    @Autowired // Inyecta el service automáticamente.
    private VentaService service;

    /**
     * Obtiene todas las ventas.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "Ventas obtenidas correctamente", "OK"));
    }

    /**
     * Obtiene una venta por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "Venta obtenida correctamente", "OK"));
    }

    /**
     * Crea una nueva venta.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody VentaDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "Venta creada correctamente", "OK"));
    }

    /**
     * Actualiza una venta existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody VentaDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "Venta actualizada correctamente", "OK"));
    }

    /**
     * Borra una venta por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "Venta eliminada correctamente", "OK"));
    }
}