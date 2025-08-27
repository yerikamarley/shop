package com.shop.service;

import com.shop.entity.Categoria;
import com.shop.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public List<Categoria> getAll() {
        return repository.findAll();
    }

    public Categoria getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Categoria no encontrado con ID: " + id));
    }

    public Categoria create(Categoria categoria) {
        return repository.save(categoria);
    }

    public Categoria update(Long id, Categoria details) {
        Categoria categoria = getById(id);
        if (details.getDescription() != null) categoria.setDescription(details.getDescription());
        return repository.save(categoria);
    }

    public void delete(Long id) {
        Categoria categoria = getById(id);
        repository.delete(categoria);
    }
}