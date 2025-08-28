package com.shop.service;

import com.shop.dto.CategoriaDTO; // Importa DTO.
import com.shop.entity.Categoria;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> getAll() {
        return repository.findAll();
    }

    public Categoria getById(Long id) {
        Optional<Categoria> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + id));
    }

    @Transactional
    public Categoria create(@Valid CategoriaDTO dto) {
        Categoria categoria = new Categoria(); // Crea la entidad.
        categoria.setDescription(dto.getDescription()); // Mapear.
        return repository.save(categoria);
    }

    @Transactional
    public Categoria update(Long id, @Valid CategoriaDTO dto) {
        Categoria existing = getById(id);
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Categoria toDelete = getById(id);
        repository.delete(toDelete);
    }
}