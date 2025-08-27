package com.shop.service;

import com.shop.entity.Proveedor;
import com.shop.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository repository;

    public List<Proveedor> getAll() {
        return repository.findAll();
    }

    public Proveedor getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado con ID: " + id));
    }

    public Proveedor create(Proveedor proveedor) {
        return repository.save(proveedor);
    }

    public Proveedor update(Long id, Proveedor details) {
        Proveedor proveedor = getById(id);
        if (details.getName() != null) proveedor.setName(details.getName());
        if (details.getAddress() != null) proveedor.setAddress(details.getAddress());
        if (details.getPhone() != null) proveedor.setPhone(details.getPhone());
        if (details.getEmail() != null) proveedor.setEmail(details.getEmail());
        return repository.save(proveedor);
    }

    public void delete(Long id) {
        Proveedor proveedor = getById(id);
        repository.delete(proveedor);
    }
}