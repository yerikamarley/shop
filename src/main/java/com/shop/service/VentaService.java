package com.shop.service;

import com.shop.dto.VentaArticuloDTO;
import com.shop.dto.VentaDTO; // Importa DTO.
import com.shop.entity.Articulo;
import com.shop.entity.Cliente;
import com.shop.entity.Venta;
import com.shop.entity.VentaArticulo;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.VentaRepository;
import com.shop.repository.ClienteRepository; // Para mapear customerId.
import com.shop.repository.ArticuloRepository; // Para mapear itemId en articulos.
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // Esto marca la clase como servicio de Spring.
public class VentaService {

    @Autowired // Esto inyecta el repository automáticamente.
    private VentaRepository repository;

    @Autowired // Esto inyecta para actualizar stock.
    private ArticuloService articuloService;

    @Autowired // Esto inyecta para mapear customerId a Cliente.
    private ClienteRepository clienteRepository;

    @Autowired // Esto inyecta para mapear itemId a Articulo en articulos.
    private ArticuloRepository articuloRepository;

    /**
     * Obtiene todas las ventas.
     * @return Lista de ventas.
     */
    public List<Venta> getAll() {
        return repository.findAll(); // Esto lista todos los registros de la DB.
    }

    /**
     * Obtiene una venta por ID.
     * @param id El ID de la venta.
     * @return La venta encontrada.
     */
    public Venta getById(Long id) {
        Optional<Venta> optional = repository.findById(id); // Esto busca opcionalmente para evitar null.
        return optional.orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
    }

    /**
     * Crea una nueva venta.
     * @param dto El DTO con datos para crear.
     * @return La venta guardada.
     */
    @Transactional // Esto asegura que la operación sea atómica.
    public Venta create(@Valid VentaDTO dto) {
        Venta venta = new Venta(); // Crea la entidad.
        venta.setTime(LocalDateTime.now()); // Setea fecha automática.
        if (dto.getCustomerId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.getCustomerId()));
            venta.setCliente(cliente);
        }
        int totalValue = 0;
        List<VentaArticulo> articulos = new ArrayList<>();
        if (dto.getArticulos() != null) {
            for (VentaArticuloDTO vaDto : dto.getArticulos()) {
                VentaArticulo va = new VentaArticulo(); // Crea línea.
                va.setVenta(venta); // Back reference.
                if (vaDto.getItemId() != null) {
                    Articulo articulo = articuloRepository.findById(vaDto.getItemId())
                        .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + vaDto.getItemId()));
                    va.setArticulo(articulo);
                    // Actualiza stock: Resta.
                    if (articulo.getQuantity() < vaDto.getQuantity()) {
                        throw new IllegalArgumentException("Stock insuficiente para " + articulo.getName());
                    }
                    articulo.setQuantity(articulo.getQuantity() - vaDto.getQuantity());
                    articuloService.update(articulo.getId(), articulo);
                }
                va.setQuantity(vaDto.getQuantity());
                va.setValue(vaDto.getValue());
                // Calcula value si no set.
                if (va.getValue() == null && va.getQuantity() != null) va.setValue(va.getQuantity() * 0); // Placeholder.
                totalValue += va.getValue() != null ? va.getValue() : 0;
                articulos.add(va);
            }
        }
        venta.setArticulos(articulos);
        venta.setValue(totalValue);
        return repository.save(venta); // Esto guarda en la DB.
    }

    /**
     * Actualiza una venta existente.
     * @param id El ID de la venta a actualizar.
     * @param dto Detalles nuevos del DTO.
     * @return La venta actualizada.
     */
    @Transactional
    public Venta update(Long id, @Valid VentaDTO dto) {
        Venta existing = getById(id); // Obtiene el existente.
        if (dto.getCustomerId() != null) {
            Cliente cliente = clienteRepository.findById(dto.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.getCustomerId()));
            existing.setCliente(cliente);
        }
        int totalValue = existing.getValue() != null ? existing.getValue() : 0;
        // Para articulos, asume no cambias; expande si necesitas.
        existing.setValue(totalValue);
        return repository.save(existing);
    }

    /**
     * Borra una venta por ID.
     * @param id El ID de la venta a borrar.
     */
    @Transactional
    public void delete(Long id) {
        Venta toDelete = getById(id); // Verifica existencia.
        if (toDelete.getArticulos() != null) {
            for (VentaArticulo va : toDelete.getArticulos()) {
                // Revierte stock: Suma de vuelta.
                Articulo articulo = articuloService.getById(va.getArticulo().getId());
                articulo.setQuantity(articulo.getQuantity() + va.getQuantity());
                articuloService.update(articulo.getId(), articulo);
            }
        }
        repository.delete(toDelete); // Borra de la DB.
    }
}