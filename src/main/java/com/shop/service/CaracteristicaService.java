package com.shop.service;

import com.shop.dto.CaracteristicaDTO; // Importa DTO.
import com.shop.entity.Caracteristica;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.CaracteristicaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CaracteristicaService {

    @Autowired
    private CaracteristicaRepository repository;

    public List<Caracteristica> getAll() {
        return repository.findAll();
    }

    public Caracteristica getById(Long id) {
        Optional<Caracteristica> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ResourceNotFoundException("Caracteristica no encontrada con ID: " + id));
    }

    @Transactional
    public Caracteristica create(@Valid CaracteristicaDTO dto) {
        Caracteristica caracteristica = new Caracteristica(); // Crea la entidad.
        caracteristica.setDescription(dto.getDescription()); // Mapear.
        return repository.save(caracteristica);
    }

    @Transactional
    public Caracteristica update(Long id, @Valid CaracteristicaDTO dto) {
        Caracteristica existing = getById(id);
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        Caracteristica toDelete = getById(id);
        repository.delete(toDelete);
    }
}