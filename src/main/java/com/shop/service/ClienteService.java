package com.shop.service;

import com.shop.dto.ClienteDTO; // Importa DTO.
import com.shop.entity.Cliente;
import com.shop.exception.ResourceNotFoundException;
import com.shop.repository.ClienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service // Esto marca la clase como servicio de Spring.
public class ClienteService {

    @Autowired // Esto inyecta el repository automáticamente.
    private ClienteRepository repository;

    /**
     * Obtiene todos los clientes.
     * @return Lista de clientes.
     */
    public List<Cliente> getAll() {
        return repository.findAll(); // Esto lista todos los registros de la DB.
    }

    /**
     * Obtiene un cliente por ID.
     * @param id El ID del cliente.
     * @return El cliente encontrado.
     */
    public Cliente getById(Long id) {
        Optional<Cliente> optional = repository.findById(id); // Esto busca opcionalmente para evitar null.
        return optional.orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    /**
     * Crea un nuevo cliente.
     * @param dto El DTO con datos para crear.
     * @return El cliente guardado.
     */
    @Transactional // Esto asegura que la operación sea atómica.
    public Cliente create(@Valid ClienteDTO dto) {
        Cliente cliente = new Cliente(); // Crea la entidad.
        cliente.setName(dto.getName()); // Mapear fields desde DTO.
        cliente.setAddress(dto.getAddress());
        cliente.setPhone(dto.getPhone());
        cliente.setEmail(dto.getEmail());
        return repository.save(cliente); // Esto guarda en la DB.
    }

    /**
     * Actualiza un cliente existente.
     * @param id El ID del cliente a actualizar.
     * @param dto Detalles nuevos del DTO.
     * @return El cliente actualizado.
     */
    @Transactional
    public Cliente update(Long id, @Valid ClienteDTO dto) {
        Cliente existing = getById(id); // Obtiene el existente.
        if (dto.getName() != null) existing.setName(dto.getName());
        if (dto.getAddress() != null) existing.setAddress(dto.getAddress());
        if (dto.getPhone() != null) existing.setPhone(dto.getPhone());
        if (dto.getEmail() != null) existing.setEmail(dto.getEmail());
        return repository.save(existing);
    }

    /**
     * Borra un cliente por ID.
     * @param id El ID del cliente a borrar.
     */
    @Transactional
    public void delete(Long id) {
        Cliente toDelete = getById(id); // Verifica existencia.
        repository.delete(toDelete); // Borra de la DB.
    }
}