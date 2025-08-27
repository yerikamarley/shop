package com.shop.service;

import com.shop.entity.Cliente;
import com.shop.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public List<Cliente> getAll() {
        return repository.findAll();
    }

    public Cliente getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));
    }

    public Cliente create(Cliente cliente) {
        return repository.save(cliente);
    }

    public Cliente update(Long id, Cliente details) {
        Cliente cliente = getById(id);
        if (details.getName() != null) cliente.setName(details.getName());
        if (details.getAddress() != null) cliente.setAddress(details.getAddress());
        if (details.getPhone() != null) cliente.setPhone(details.getPhone());
        if (details.getEmail() != null) cliente.setEmail(details.getEmail());
        return repository.save(cliente);
    }

    public void delete(Long id) {
        Cliente cliente = getById(id);
        repository.delete(cliente);
    }
}