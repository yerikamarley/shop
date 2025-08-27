package com.shop.service;

import com.shop.entity.Venta;
import com.shop.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository repository;

    public List<Venta> getAll() {
        return repository.findAll();
    }

    public Venta getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Venta no encontrado con ID: " + id));
    }

    public Venta create(Venta venta) {
        return repository.save(venta);
    }

    public Venta update(Long id, Venta details) {
        Venta venta = getById(id);
        if (details.getTime() != null) venta.setTime(details.getTime());
        if (details.getCliente() != null) venta.setCliente(details.getCliente());
        if (details.getValue() != null) venta.setValue(details.getValue());
        return repository.save(venta);
    }

    public void delete(Long id) {
        Venta venta = getById(id);
        repository.delete(venta);
    }
}