package com.shop.service;

import com.shop.entity.Articulo;
import com.shop.repository.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticuloService {

    @Autowired
    private ArticuloRepository repository;

    public List<Articulo> getAll() {
        return repository.findAll();
    }

    public Articulo getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Artículo no encontrado con ID: " + id));
    }

    public Articulo create(Articulo articulo) {
        return repository.save(articulo);
    }

    public Articulo update(Long id, Articulo details) {
        Articulo articulo = getById(id);
        if (details.getName() != null) articulo.setName(details.getName());
        if (details.getDescription() != null) articulo.setDescription(details.getDescription());
        if (details.getCategoria() != null) articulo.setCategoria(details.getCategoria());
        if (details.getQuantity() != null) articulo.setQuantity(details.getQuantity());
        if (details.getState() != null) articulo.setState(details.getState());
        return repository.save(articulo);
    }

    public void delete(Long id) {
        Articulo articulo = getById(id);
        repository.delete(articulo);
    }
}