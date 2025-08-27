package com.shop.service;

import com.shop.entity.VentaArticulo;
import com.shop.repository.VentaArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentaArticuloService {

    @Autowired
    private VentaArticuloRepository repository;

    public List<VentaArticulo> getAll() {
        return repository.findAll();
    }

    public VentaArticulo getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("VentaArticulo no encontrado con ID: " + id));
    }

    public VentaArticulo create(VentaArticulo ventaArticulo) {
        return repository.save(ventaArticulo);
    }

    public VentaArticulo update(Long id, VentaArticulo details) {
        VentaArticulo ventaArticulo = getById(id);
        if (details.getVenta() != null) ventaArticulo.setVenta(details.getVenta());
        if (details.getArticulo() != null) ventaArticulo.setArticulo(details.getArticulo());
        if (details.getQuantity() != null) ventaArticulo.setQuantity(details.getQuantity());
        if (details.getValue() != null) ventaArticulo.setValue(details.getValue());
        return repository.save(ventaArticulo);
    }

    public void delete(Long id) {
        VentaArticulo ventaArticulo = getById(id);
        repository.delete(ventaArticulo);
    }
}