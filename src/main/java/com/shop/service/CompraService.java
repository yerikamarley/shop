package com.shop.service;

import com.shop.entity.Compra;
import com.shop.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository repository;

    public List<Compra> getAll() {
        return repository.findAll();
    }

    public Compra getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrado con ID: " + id));
    }

    public Compra create(Compra compra) {
        return repository.save(compra);
    }

    public Compra update(Long id, Compra details) {
        Compra compra = getById(id);
        if (details.getTime() != null) compra.setTime(details.getTime());
        if (details.getProveedor() != null) compra.setProveedor(details.getProveedor());
        if (details.getValue() != null) compra.setValue(details.getValue());
        return repository.save(compra);
    }

    public void delete(Long id) {
        Compra compra = getById(id);
        repository.delete(compra);
    }
}