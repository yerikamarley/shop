package com.shop.service;

import com.shop.dto.CompraArticuloDTO;
import com.shop.dto.CompraDTO;
import com.shop.entity.Articulo;
import com.shop.entity.Compra;
import com.shop.entity.CompraArticulo;
import com.shop.entity.Proveedor;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.CompraRepository;
import com.shop.repository.ProveedorRepository;
import com.shop.repository.ArticuloRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Esto marca la clase como servicio de Spring.
public class CompraService {

    @Autowired // Esto inyecta el repository automáticamente.
    private CompraRepository repository;

    @Autowired // Esto inyecta para actualizar stock.
    private ArticuloService articuloService;

    @Autowired // Esto inyecta para mapear providerId a Proveedor.
    private ProveedorRepository proveedorRepository;

    @Autowired // Esto inyecta para mapear itemId a Articulo en articulos.
    private ArticuloRepository articuloRepository;

    /**
     * Obtiene todas las compras.
     * @return Lista de compras.
     */
    public List<Compra> getAll() {
        return repository.findAll(); // Esto lista todos los registros de la DB.
    }

    /**
     * Obtiene una compra por ID.
     * @param id El ID de la compra.
     * @return La compra encontrada.
     */
    public Compra getById(Long id) {
        Optional<Compra> optional = repository.findById(id); // Esto busca opcionalmente para evitar null.
        return optional.orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con ID: " + id));
    }

    /**
     * Crea una nueva compra.
     * @param dto El DTO con datos para crear.
     * @return La compra guardada.
     */
    @Transactional // Esto asegura que la operación sea atómica.
    public Compra create(@Valid CompraDTO dto) {
        Compra compra = new Compra(); // Crea la entidad.
        compra.setTime(LocalDateTime.now()); // Setea fecha automática.
        if (dto.getProviderId() != null) {
            Proveedor proveedor = proveedorRepository.findById(dto.getProviderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + dto.getProviderId()));
            compra.setProveedor(proveedor);
        }
        int totalValue = 0;
        List<CompraArticulo> articulos = new ArrayList<>();
        if (dto.getArticulos() != null) {
            for (CompraArticuloDTO caDto : dto.getArticulos()) {
                CompraArticulo ca = new CompraArticulo(); // Crea línea.
                ca.setCompra(compra); // Back reference.
                if (caDto.getItemId() != null) {
                    Articulo articulo = articuloRepository.findById(caDto.getItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + caDto.getItemId()));
                    ca.setArticulo(articulo);
                    // Actualiza stock: Suma.
                    articulo.setQuantity(articulo.getQuantity() + caDto.getQuantity());
                    articuloService.update(articulo.getId(), articulo);
                }
                ca.setQuantity(caDto.getQuantity());
                ca.setUniValue(caDto.getUniValue());
                if (ca.getUniValue() != null && ca.getQuantity() != null) {
                    ca.setValue(ca.getQuantity() * ca.getUniValue());
                }
                totalValue += ca.getValue() != null ? ca.getValue() : 0;
                articulos.add(ca);
            }
        }
        compra.setArticulos(articulos);
        compra.setValue(totalValue);
        return repository.save(compra); // Esto guarda en la DB.
    }

    /**
     * Actualiza una compra existente.
     * @param id El ID de la compra a actualizar.
     * @param dto Detalles nuevos del DTO.
     * @return La compra actualizada.
     */
    @Transactional
    public Compra update(Long id, @Valid CompraDTO dto) {
        Compra existing = getById(id); // Obtiene el existente.
        if (dto.getProviderId() != null) {
            Proveedor proveedor = proveedorRepository.findById(dto.getProviderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + dto.getProviderId()));
            existing.setProveedor(proveedor);
        }
        int totalValue = existing.getValue() != null ? existing.getValue() : 0;
        // Para articulos, asume no cambias; expande si necesitas.
        existing.setValue(totalValue);
        return repository.save(existing);
    }

    /**
     * Borra una compra por ID.
     * @param id El ID de la compra a borrar.
     */
    @Transactional
    public void delete(Long id) {
        Compra toDelete = getById(id); // Verifica existencia.
        if (toDelete.getArticulos() != null) {
            for (CompraArticulo ca : toDelete.getArticulos()) {
                // Revierte stock: Resta.
                Articulo articulo = articuloService.getById(ca.getArticulo().getId());
                articulo.setQuantity(articulo.getQuantity() - ca.getQuantity());
                articuloService.update(articulo.getId(), articulo);
            }
        }
        repository.delete(toDelete); // Borra de la DB.
    }
}