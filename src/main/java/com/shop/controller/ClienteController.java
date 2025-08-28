package com.shop.controller;

import com.shop.dto.ClienteDTO;
import com.shop.exception.ResponseDTO;
import com.shop.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // Esto hace que la clase maneje requests REST con JSON.
@RequestMapping("/api/clientes") // Ruta base para este controller.
public class ClienteController {

    @Autowired // Inyecta el service automáticamente.
    private ClienteService service;

    /**
     * Obtiene todos los clientes.
     */
    @GetMapping
    public ResponseEntity<ResponseDTO> getAll() {
        return ResponseEntity.ok(new ResponseDTO(service.getAll(), "Clientes obtenidos correctamente", "OK"));
    }

    /**
     * Obtiene un cliente por ID.
     * @param id El ID a buscar.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ResponseDTO(service.getById(id), "Cliente obtenido correctamente", "OK"));
    }

    /**
     * Crea un nuevo cliente.
     * @param dto El DTO con datos para crear.
     */
    @PostMapping
    public ResponseEntity<ResponseDTO> create(@Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(201).body(new ResponseDTO(service.create(dto), "Cliente creado correctamente", "OK"));
    }

    /**
     * Actualiza un cliente existente.
     * @param id El ID a actualizar.
     * @param dto Detalles nuevos del DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.ok(new ResponseDTO(service.update(id, dto), "Cliente actualizado correctamente", "OK"));
    }

    /**
     * Borra un cliente por ID.
     * @param id El ID a borrar.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ResponseDTO(null, "Cliente eliminado correctamente", "OK"));
    }
}