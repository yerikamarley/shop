package com.shop.service;

import com.shop.entity.Caracteristica;
import com.shop.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CaracteristicaService {

    @Autowired
    private CaracteristicaRepository repository;

    public List<Caracteristica> getAll() {
        return repository.findAll();
    }

    public Caracteristica getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Caracteristica no encontrado con ID: " + id));
    }

    public Caracteristica create(Caracteristica caracteristica) {
        return repository.save(caracteristica);
    }

    public Caracteristica update(Long id, Caracteristica details) {
        Caracteristica caracteristica = getById(id);
        if (details.getDescription() != null) caracteristica.setDescription(details.getDescription());
        return repository.save(caracteristica);
    }

    public void delete(Long id) {
        Caracteristica caracteristica = getById(id);
        repository.delete(caracteristica);
    }
}