package com.shop.service;

import com.shop.entity.ArticuloCaracteristica;
import com.shop.repository.ArticuloCaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticuloCaracteristicaService {

    @Autowired
    private ArticuloCaracteristicaRepository repository;

    public List<ArticuloCaracteristica> getAll() {
        return repository.findAll();
    }

    public ArticuloCaracteristica getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("ArticuloCaracteristica no encontrado con ID: " + id));
    }

    public ArticuloCaracteristica create(ArticuloCaracteristica articuloCaracteristica) {
        return repository.save(articuloCaracteristica);
    }

    public ArticuloCaracteristica update(Long id, ArticuloCaracteristica details) {
        ArticuloCaracteristica articuloCaracteristica = getById(id);
        if (details.getArticulo() != null) articuloCaracteristica.setArticulo(details.getArticulo());
        if (details.getCaracteristica() != null) articuloCaracteristica.setCaracteristica(details.getCaracteristica());
        if (details.getValue() != null) articuloCaracteristica.setValue(details.getValue());
        return repository.save(articuloCaracteristica);
    }

    public void delete(Long id) {
        ArticuloCaracteristica articuloCaracteristica = getById(id);
        repository.delete(articuloCaracteristica);
    }
}