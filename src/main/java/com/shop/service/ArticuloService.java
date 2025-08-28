package com.shop.service;

import com.shop.dto.ArticuloDTO;
import com.shop.entity.Articulo;
import com.shop.entity.Categoria;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.ArticuloRepository;
import com.shop.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service // Esto marca la clase como servicio de Spring.
public class ArticuloService {

    @Autowired // Esto inyecta el repository automáticamente.
    private ArticuloRepository repository;

    @Autowired // Esto inyecta para mapear categoryId a Categoria.
    private CategoriaRepository categoriaRepository;

    /**
     * Obtiene todos los artículos.
     * @return Lista de artículos.
     */
    public List<Articulo> getAll() {
        return repository.findAll(); // Esto lista todos los registros de la DB.
    }

    /**
     * Obtiene un artículo por ID.
     * @param id El ID del artículo.
     * @return El artículo encontrado.
     */
    public Articulo getById(Long id) {
        Optional<Articulo> optional = repository.findById(id); // Esto busca opcionalmente para evitar null.
        return optional.orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado con ID: " + id));
    }

    /**
     * Crea un nuevo artículo.
     * @param dto El DTO con datos para crear.
     * @return El artículo guardado.
     */
    @Transactional // Esto asegura que la operación sea atómica.
    public Articulo create(@Valid ArticuloDTO dto) {
        Articulo articulo = new Articulo(); // Crea la entidad.
        articulo.setName(dto.getName()); // Mapear fields desde DTO.
        articulo.setDescription(dto.getDescription());
        if (dto.getCategoryId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + dto.getCategoryId()));
            articulo.setCategoria(categoria);
        }
        articulo.setQuantity(dto.getQuantity());
        articulo.setState(dto.getState());
        // Validación manual: Cantidad no negativa.
        if (articulo.getQuantity() != null && articulo.getQuantity() < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa");
        }
        return repository.save(articulo); // Esto guarda en la DB.
    }

    /**
     * Actualiza un artículo existente.
     * @param id El ID del artículo a actualizar.
     * @param dto Detalles nuevos del DTO.
     * @return El artículo actualizado.
     */
    @Transactional
    public Articulo update(Long id, @Valid ArticuloDTO dto) {
        Articulo existing = getById(id); // Obtiene el existente.
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getDescription() != null) existing.setDescription(dto.getDescription());
        if (dto.getCategoryId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + dto.getCategoryId()));
            existing.setCategoria(categoria);
        }
        if (dto.getQuantity() != null) {
            if (dto.getQuantity() < 0) throw new IllegalArgumentException("La cantidad no puede ser negativa");
            existing.setQuantity(dto.getQuantity());
        }
        if (dto.getState() != null) existing.setState(dto.getState());
        return repository.save(existing);
    }

    /**
     * Borra un artículo por ID.
     * @param id El ID del artículo a borrar.
     */
    @Transactional
    public void delete(Long id) {
        Articulo toDelete = getById(id); // Verifica existencia.
        repository.delete(toDelete); // Borra de la DB.
    }

	public void update(Long id, Articulo articulo) {
		// TODO Auto-generated method stub
		
	}
}