package com.shop.service;

import com.shop.dto.VentaArticuloDTO; // Importa DTO.
import com.shop.entity.Articulo;
import com.shop.entity.Venta;
import com.shop.entity.VentaArticulo;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.VentaArticuloRepository;
import com.shop.repository.ArticuloRepository; // Para mapear itemId.
import com.shop.repository.VentaRepository; // Para mapear salesId.
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class VentaArticuloService {

    @Autowired
    private VentaArticuloRepository repository;

    @Autowired // Para mapear itemId.
    private ArticuloRepository articuloRepository;

    @Autowired // Para mapear salesId.
    private VentaRepository ventaRepository;

    public List<VentaArticulo> getAll() {
        return repository.findAll();
    }

    public VentaArticulo getById(Long id) {
        Optional<VentaArticulo> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ResourceNotFoundException("VentaArticulo no encontrado con ID: " + id));
    }

    @Transactional
    public VentaArticulo create(@Valid VentaArticuloDTO dto) {
        VentaArticulo va = new VentaArticulo(); // Crea la entidad.
        if (dto.getSalesId() != null) {
            Venta venta = ventaRepository.findById(dto.getSalesId())
                    .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + dto.getSalesId()));
            va.setVenta(venta);
        }
        if (dto.getItemId() != null) {
            Articulo articulo = articuloRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + dto.getItemId()));
            va.setArticulo(articulo);
        }
        va.setQuantity(dto.getQuantity());
        va.setValue(dto.getValue());
        // Calcula value si no set (placeholder).
        if (va.getValue() == null && va.getQuantity() != null) va.setValue(va.getQuantity() * 0);
        return repository.save(va);
    }

    @Transactional
    public VentaArticulo update(Long id, @Valid VentaArticuloDTO dto) {
        VentaArticulo existing = getById(id);
        if (dto.getSalesId() != null) {
            Venta venta = ventaRepository.findById(dto.getSalesId())
                    .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + dto.getSalesId()));
            existing.setVenta(venta);
        }
        if (dto.getItemId() != null) {
            Articulo articulo = articuloRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + dto.getItemId()));
            existing.setArticulo(articulo);
        }
        if (dto.getQuantity() != null) existing.setQuantity(dto.getQuantity());
        if (dto.getValue() != null) existing.setValue(dto.getValue());
        // Recalcula value.
        if (existing.getQuantity() != null) existing.setValue(existing.getQuantity() * 0); // Placeholder.
        return repository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        VentaArticulo toDelete = getById(id);
        repository.delete(toDelete);
    }
}