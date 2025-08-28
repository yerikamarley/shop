package com.shop.controller;

import com.shop.dto.CaracteristicaDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.CaracteristicaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/caracteristicas") // Ruta base para este controller.
public class CaracteristicaController {

    @Autowired // Inyecta el service automáticamente.
    private CaracteristicaService service;

    /**
     * Obtiene todas las características.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "Caracteristicas obtenidas correctamente", "OK"));
    }

    /**
     * Obtiene una característica por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "Caracteristica obtenida correctamente", "OK"));
    }

    /**
     * Crea una nueva característica.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody CaracteristicaDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "Caracteristica creada correctamente", "OK"));
    }

    /**
     * Actualiza una característica existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CaracteristicaDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "Caracteristica actualizada correctamente", "OK"));
    }

    /**
     * Borra una característica por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "Caracteristica eliminada correctamente", "OK"));
    }
}