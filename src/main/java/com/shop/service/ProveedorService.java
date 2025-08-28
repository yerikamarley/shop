package com.shop.service;

import com.shop.dto.ProveedorDTO; // Importa DTO.
import com.shop.entity.Proveedor;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.ProveedorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    public List<Proveedor> getAll() {
        return repository.findAll();
    }

    public Proveedor getById(Long id) {
        Optional<Proveedor> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
    }

    @Transactional
    public Proveedor create(@Valid ProveedorDTO dto) {
        Proveedor proveedor = new Proveedor(); // Crea la entidad.
        proveedor.setName(dto.getName()); // Mapear.
        proveedor.setAddress(dto.getAddress());
        proveedor.setPhone(dto.getPhone());
        proveedor.setEmail(dto.getEmail());
        return repository.save(proveedor);
    }

    @Transactional
    public Proveedor update(Long id, @Valid ProveedorDTO dto) {
        Proveedor existing = getById(id);
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Proveedor toDelete = getById(id);
        repository.delete(toDelete);
    }
}