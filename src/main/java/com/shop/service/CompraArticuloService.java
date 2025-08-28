package com.shop.service;

import com.shop.dto.CompraArticuloDTO;
import com.shop.entity.Articulo;
import com.shop.entity.Compra;
import com.shop.entity.CompraArticulo;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.CompraArticuloRepository;
import com.shop.repository.ArticuloRepository;
import com.shop.repository.CompraRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service // Esto marca la clase como servicio de Spring.
public class CompraArticuloService {

    @Autowired // Esto inyecta el repository automáticamente.
    private CompraArticuloRepository repository;

    @Autowired // Esto inyecta para mapear itemId a Articulo.
    private ArticuloRepository articuloRepository;

    @Autowired // Esto inyecta para mapear purchaseId a Compra.
    private CompraRepository compraRepository;

    /**
     * Obtiene todas las líneas de compra.
     * @return Lista de líneas de compra.
     */
    public List<CompraArticulo> getAll() {
        return repository.findAll(); // Esto lista todos los registros de la DB.
    }

    /**
     * Obtiene una línea de compra por ID.
     * @param id El ID de la línea de compra.
     * @return La línea de compra encontrada.
     */
    public CompraArticulo getById(Long id) {
        Optional<CompraArticulo> optional = repository.findById(id); // Esto busca opcionalmente para evitar null.
        return optional.orElseThrow(() -> new ResourceNotFoundException("CompraArticulo no encontrado con ID: " + id));
    }

    /**
     * Crea una nueva línea de compra.
     * @param dto El DTO con datos para crear.
     * @return La línea de compra guardada.
     */
    @Transactional // Esto asegura que la operación sea atómica.
    public CompraArticulo create(@Valid CompraArticuloDTO dto) {
        CompraArticulo compraArticulo = new CompraArticulo(); // Crea la entidad.
        if (dto.getItemId() != null) {
            Articulo articulo = articuloRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + dto.getItemId()));
            compraArticulo.setArticulo(articulo);
        }
        compraArticulo.setQuantity(dto.getQuantity());
        if (dto.getPurchaseId() != null) {
            Compra compra = compraRepository.findById(dto.getPurchaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con ID: " + dto.getPurchaseId()));
            compraArticulo.setCompra(compra);
        }
        compraArticulo.setUniValue(dto.getUniValue());
        compraArticulo.setValue(dto.getValue());
        // Calcula value automáticamente si no set.
        if (compraArticulo.getUniValue() != null && compraArticulo.getQuantity() != null) {
            compraArticulo.setValue(compraArticulo.getQuantity() * compraArticulo.getUniValue());
        }
        return repository.save(compraArticulo); // Esto guarda en la DB.
    }

    /**
     * Actualiza una línea de compra existente.
     * @param id El ID de la línea de compra a actualizar.
     * @param dto Detalles nuevos del DTO.
     * @return La línea de compra actualizada.
     */
    @Transactional
    public CompraArticulo update(Long id, @Valid CompraArticuloDTO dto) {
        CompraArticulo existing = getById(id); // Obtiene el existente.
        if (dto.getItemId() != null) {
            Articulo articulo = articuloRepository.findById(dto.getItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + dto.getItemId()));
            existing.setArticulo(articulo);
        }
        if (dto.getQuantity() != null) existing.setQuantity(dto.getQuantity());
        if (dto.getPurchaseId() != null) {
            Compra compra = compraRepository.findById(dto.getPurchaseId())
                    .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con ID: " + dto.getPurchaseId()));
            existing.setCompra(compra);
        }
        if (dto.getUniValue() != null) existing.setUniValue(dto.getUniValue());
        if (dto.getValue() != null) existing.setValue(dto.getValue());
        // Recalcula value si cambia.
        if (existing.getUniValue() != null && existing.getQuantity() != null) {
            existing.setValue(existing.getQuantity() * existing.getUniValue());
        }
        return repository.save(existing);
    }

    /**
     * Borra una línea de compra por ID.
     * @param id El ID de la línea de compra a borrar.
     */
    @Transactional
    public void delete(Long id) {
        CompraArticulo toDelete = getById(id); // Verifica existencia.
        repository.delete(toDelete); // Borra de la DB.
    }
}