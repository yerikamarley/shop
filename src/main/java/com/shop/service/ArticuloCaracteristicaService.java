package com.shop.service;

import com.shop.dto.ArticuloCaracteristicaDTO; // Importa DTO.
import com.shop.entity.Articulo;
import com.shop.entity.ArticuloCaracteristica;
import com.shop.entity.Caracteristica;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.ArticuloCaracteristicaRepository;
import com.shop.repository.ArticuloRepository; // Para mapear itemId.
import com.shop.repository.CaracteristicaRepository; // Para mapear characteristicId.
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticuloCaracteristicaService {

    @Autowired
    private ArticuloCaracteristicaRepository repository;

    @Autowired // Para mapear itemId.
    private ArticuloRepository articuloRepository;

    @Autowired // Para mapear characteristicId.
    private CaracteristicaRepository caracteristicaRepository;

    public List<ArticuloCaracteristica> getAll() {
        return repository.findAll();
    }

    public ArticuloCaracteristica getById(Long id) {
        Optional<ArticuloCaracteristica> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ResourceNotFoundException("ArticuloCaracteristica no encontrado con ID: " + id));
    }

    @Transactional
    public ArticuloCaracteristica create(@Valid ArticuloCaracteristicaDTO dto) {
        ArticuloCaracteristica ac = new ArticuloCaracteristica(); // Crea la entidad.
        ac.setValue(dto.getValue()); // Mapear.
        if (dto.getItemId() != null) {
            Articulo articulo = articuloRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + dto.getItemId()));
            ac.setArticulo(articulo);
        }
        if (dto.getCharacteristicId() != null) {
            Caracteristica caracteristica = caracteristicaRepository.findById(dto.getCharacteristicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Caracteristica no encontrada con ID: " + dto.getCharacteristicId()));
            ac.setCaracteristica(caracteristica);
        }
        if (ac.getArticulo() == null || ac.getCaracteristica() == null) {
            throw new IllegalArgumentException("Articulo y Caracteristica son requeridos");
        }
        return repository.save(ac);
    }

    @Transactional
    public ArticuloCaracteristica update(Long id, @Valid ArticuloCaracteristicaDTO dto) {
        ArticuloCaracteristica existing = getById(id);
        if (dto.getValue() != null) existing.setValue(dto.getValue());
        if (dto.getItemId() != null) {
            Articulo articulo = articuloRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + dto.getItemId()));
            existing.setArticulo(articulo);
        }
        if (dto.getCharacteristicId() != null) {
            Caracteristica caracteristica = caracteristicaRepository.findById(dto.getCharacteristicId())
                    .orElseThrow(() -> new ResourceNotFoundException("Caracteristica no encontrada con ID: " + dto.getCharacteristicId()));
            existing.setCaracteristica(caracteristica);
        }
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        ArticuloCaracteristica toDelete = getById(id);
        repository.delete(toDelete);
    }

    // Método extra: Busca características por ID de artículo.
    public List<ArticuloCaracteristica> findByArticuloId(Long articuloId) {
        // Simulado con stream; agrega findByArticuloId en repo para query real.
        return getAll().stream()
                .filter(ac -> ac.getArticulo().getId().equals(articuloId))
                .collect(Collectors.toList());
    }
}