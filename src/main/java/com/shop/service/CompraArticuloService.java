package com.shop.service;

import com.shop.entity.CompraArticulo;
import com.shop.repository.CompraArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompraArticuloService {

    @Autowired
    private CompraArticuloRepository repository;

    public List<CompraArticulo> getAll() {
        return repository.findAll();
    }

    public CompraArticulo getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("CompraArticulo no encontrado con ID: " + id));
    }

    public CompraArticulo create(CompraArticulo compraArticulo) {
        return repository.save(compraArticulo);
    }

    public CompraArticulo update(Long id, CompraArticulo details) {
        CompraArticulo compraArticulo = getById(id);
        if (details.getArticulo() != null) compraArticulo.setArticulo(details.getArticulo());
        if (details.getQuantity() != null) compraArticulo.setQuantity(details.getQuantity());
        if (details.getCompra() != null) compraArticulo.setCompra(details.getCompra());
        if (details.getUniValue() != null) compraArticulo.setUniValue(details.getUniValue());
        if (details.getValue() != null) compraArticulo.setValue(details.getValue());
        return repository.save(compraArticulo);
    }

    public void delete(Long id) {
        CompraArticulo compraArticulo = getById(id);
        repository.delete(compraArticulo);
    }
}